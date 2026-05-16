package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.*;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.PistaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.ReservaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.UsuarioRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationsService {

    private static final Logger log = LoggerFactory.getLogger(ReservationsService.class);

    private final ReservaRepo reservaRepo;
    private final UsuarioRepo usuarioRepo;
    private final PistaRepo pistaRepo;

    public ReservationsService(ReservaRepo reservaRepo, UsuarioRepo usuarioRepo, PistaRepo pistaRepo) {
        this.reservaRepo = reservaRepo;
        this.usuarioRepo = usuarioRepo;
        this.pistaRepo = pistaRepo;
    }

    // Crear reserva
    public Reserva crearReserva(Authentication auth, ReservaRequest reservaRequest) {
        log.info("Servicio: creando reserva para pista {} el {} a las {}",
                reservaRequest.getIdPista(), reservaRequest.getFechaReserva(), reservaRequest.getHoraInicio());

        Usuario usuario = resolverUsuario(auth);

        // 404 si la pista no existe
        Pista pista = pistaRepo.findById(reservaRequest.getIdPista())
                .orElseThrow(() -> {
                    log.error("Servicio: pista {} no encontrada al crear reserva", reservaRequest.getIdPista());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        // 409 si la pista está inactiva
        if (!pista.isActiva()) {
            log.error("Servicio: la pista {} está inactiva, no se puede reservar", pista.getIdPista());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        // Calcular hora de fin
        LocalTime horaFin = reservaRequest.getHoraInicio().plusMinutes(reservaRequest.getDuracionMinutos());

        // Comprobar solapamiento con reservas activas de esa pista en ese día
        List<Reserva> reservasDelDia =
                reservaRepo.findByPista_IdPistaAndFechaReserva(reservaRequest.getIdPista(), reservaRequest.getFechaReserva());

        boolean ocupado = false;

        for (Reserva r : reservasDelDia) {
            if (r.getEstado() == Reserva.Estado.ACTIVA) {
                // Hay solapamiento si: inicio_nueva < fin_existente Y inicio_existente < fin_nueva
                if (reservaRequest.getHoraInicio().isBefore(r.getHoraFin())
                        && r.getHoraInicio().isBefore(horaFin)) {
                    ocupado = true;
                    break;
                }
            }
        }

        if (ocupado) {
            log.error("Servicio: la pista {} ya tiene una reserva activa en ese horario", reservaRequest.getIdPista());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        // Crear y guardar la reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setPista(pista);
        reserva.setFechaReserva(reservaRequest.getFechaReserva());
        reserva.setHoraInicio(reservaRequest.getHoraInicio());
        reserva.setDuracionMinutos(reservaRequest.getDuracionMinutos());
        reserva.setHoraFin(horaFin);
        reserva.setEstado(Reserva.Estado.ACTIVA);
        reserva.setFechaCreacion(LocalDateTime.now());

        Reserva guardada = reservaRepo.save(reserva);
        log.info("Servicio: reserva creada con id {} para usuario {} en pista {}",
                guardada.getIdReserva(), usuario.getEmail(), pista.getNombre());
        return guardada;
    }

    // Listar reservas
    public List<Reserva> listarReservas(Authentication auth) {
        Usuario usuario = resolverUsuario(auth);
        log.info("Servicio: listando reservas para usuario {} (rol: {})", usuario.getEmail(), usuario.getRol());

        if (esAdmin(usuario)) {
            List<Reserva> todas = new ArrayList<>();
            reservaRepo.findAll().forEach(todas::add);
            log.info("Servicio: devolviendo {} reservas (admin, todas)", todas.size());
            return todas;
        }

        List<Reserva> reservas = reservaRepo.findByUsuario_IdUsuario(usuario.getIdUsuario());
        log.info("Servicio: devolviendo {} reservas del usuario {}", reservas.size(), usuario.getEmail());
        return reservas;
    }

    // Obtener una reserva concreta
    public Reserva getReserva(Authentication auth, int id) {
        log.info("Servicio: buscando reserva con id {}", id);
        Usuario usuario = resolverUsuario(auth);

        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Servicio: reserva con id {} no encontrada", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        // 403 si no es suya y no es admin
        if (!esAdmin(usuario) && !reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            log.error("Servicio: usuario {} no tiene permiso para ver la reserva {}", usuario.getEmail(), id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        log.info("Servicio: reserva {} encontrada y autorizada para usuario {}", id, usuario.getEmail());
        return reserva;
    }

    // Modificar reserva
    public Reserva reprogramarReserva(Authentication auth, int id, ReservaRequest reservaRequest) {
        log.info("Servicio: reprogramando reserva con id {} a fecha {} hora {}",
                id, reservaRequest.getFechaReserva(), reservaRequest.getHoraInicio());

        Usuario usuario = resolverUsuario(auth);

        Reserva existente = reservaRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Servicio: reserva {} no encontrada para reprogramar", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        // 403 si no es suya y no es admin
        if (!esAdmin(usuario) && !existente.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            log.error("Servicio: usuario {} no tiene permiso para modificar la reserva {}", usuario.getEmail(), id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        LocalTime nuevaHoraFin = reservaRequest.getHoraInicio().plusMinutes(reservaRequest.getDuracionMinutos());

        // Comprobar solapamiento con otras reservas de esa pista en el nuevo día (excluyendo la propia)
        List<Reserva> reservasDelDia =
                reservaRepo.findByPista_IdPistaAndFechaReserva(
                        existente.getPista().getIdPista(),
                        reservaRequest.getFechaReserva()
                );

        boolean ocupado = false;

        for (Reserva r : reservasDelDia) {
            if (!r.getIdReserva().equals(id) && r.getEstado() == Reserva.Estado.ACTIVA) {
                if (reservaRequest.getHoraInicio().isBefore(r.getHoraFin())
                        && r.getHoraInicio().isBefore(nuevaHoraFin)) {
                    ocupado = true;
                    break;
                }
            }
        }

        if (ocupado) {
            log.error("Servicio: nuevo horario de reserva {} ya está ocupado", id);
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        existente.setFechaReserva(reservaRequest.getFechaReserva());
        existente.setHoraInicio(reservaRequest.getHoraInicio());
        existente.setDuracionMinutos(reservaRequest.getDuracionMinutos());
        existente.setHoraFin(nuevaHoraFin);

        Reserva modificada = reservaRepo.save(existente);
        log.info("Servicio: reserva {} reprogramada correctamente", id);
        return modificada;
    }

    // Cancelar reserva
    public void cancelarReserva(Authentication auth, int id) {
        log.info("Servicio: cancelando reserva con id {}", id);
        Usuario usuario = resolverUsuario(auth);

        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Servicio: reserva {} no encontrada para cancelar", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        // 403 si no es suya y no es admin
        if (!esAdmin(usuario) && !reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            log.error("Servicio: usuario {} no tiene permiso para cancelar la reserva {}", usuario.getEmail(), id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        reserva.setEstado(Reserva.Estado.CANCELADA);
        reservaRepo.save(reserva);
        log.info("Servicio: reserva {} cancelada correctamente", id);
    }

    // Devuelve todas las reservas (uso interno, p.ej. tarea programada)
    public List<Reserva> getAllInternal() {
        log.debug("Servicio: obteniendo todas las reservas (uso interno)");
        List<Reserva> reservas = new ArrayList<>();
        reservaRepo.findAll().forEach(reservas::add);
        return reservas;
    }

    // ── Métodos auxiliares privados ──────────────────────────────────────────

    private boolean esAdmin(Usuario usuario) {
        return usuario.getRol() == Rol.ADMIN;
    }

    private Usuario resolverUsuario(Authentication auth) {
        if (auth == null) {
            log.error("Servicio: intento de acceso sin autenticación");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String email = auth.getName();
        Optional<Usuario> usuario = usuarioRepo.findByEmailIgnoreCase(email);

        if (usuario.isEmpty()) {
            log.error("Servicio: usuario autenticado {} no encontrado en base de datos", email);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return usuario.get();
    }
}
