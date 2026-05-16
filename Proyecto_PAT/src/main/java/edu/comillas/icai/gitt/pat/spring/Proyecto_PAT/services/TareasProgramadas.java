package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.PistaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.ReservaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.UsuarioRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TareasProgramadas {

    private static final Logger log = LoggerFactory.getLogger(TareasProgramadas.class);

    // Horario del club: de 09:00 a 22:00, slots de 60 minutos
    private static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);
    private static final LocalTime HORA_CIERRE   = LocalTime.of(22, 0);
    private static final int SLOT_MINUTOS        = 60;

    private final ReservaRepo reservaRepo;
    private final UsuarioRepo usuarioRepo;
    private final PistaRepo pistaRepo;

    public TareasProgramadas(ReservaRepo reservaRepo, UsuarioRepo usuarioRepo, PistaRepo pistaRepo) {
        this.reservaRepo = reservaRepo;
        this.usuarioRepo = usuarioRepo;
        this.pistaRepo   = pistaRepo;
    }

    // ── Recordatorio diario: avisa a cada usuario de sus reservas de hoy ─────
    // Se ejecuta cada día a las 02:00
    @Scheduled(cron = "0 0 2 * * *")
    public void enviarRecordatorioReservas() {
        log.info("Tarea programada: iniciando recordatorio diario de reservas");

        LocalDate hoy = LocalDate.now();
        List<Reserva> reservasDeHoy = reservaRepo.findByFechaReserva(hoy);

        for (Reserva reserva : reservasDeHoy) {
            if (reserva.getUsuario() != null && reserva.getEstado() == Reserva.Estado.ACTIVA) {
                log.info("Recordatorio enviado a {} → reserva en pista '{}' hoy a las {}",
                        reserva.getUsuario().getEmail(),
                        reserva.getPista().getNombre(),
                        reserva.getHoraInicio());
            }
        }

        log.info("Tarea programada: recordatorio diario completado ({} reservas activas hoy)", reservasDeHoy.size());
    }

    // ── Boletín mensual: envía a cada usuario la disponibilidad de las pistas ─
    // Se ejecuta el día 1 de cada mes a las 02:00
    @Scheduled(cron = "0 0 2 1 * *")
    public void enviarResumenMensualDisponibilidad() {
        log.info("Tarea programada: iniciando boletín mensual de disponibilidad");

        // Calculamos disponibilidad para el día de hoy (primer día del mes)
        LocalDate hoy = LocalDate.now();

        // Obtenemos todas las pistas activas
        List<Pista> pistas = pistaRepo.findByActivaTrue();

        // Para cada pista, calculamos qué horas están libres hoy
        // Guardamos el resultado en una lista de textos para incluir en el boletín
        List<String> lineaBoletín = new ArrayList<>();

        for (Pista pista : pistas) {

            // Obtenemos las reservas activas de esta pista para hoy
            List<Reserva> reservasDelDia =
                    reservaRepo.findByPista_IdPistaAndFechaReserva(pista.getIdPista(), hoy);

            // Generamos todos los slots del día (09:00, 10:00, ... 21:00)
            List<LocalTime> slotsLibres = new ArrayList<>();
            LocalTime slot = HORA_APERTURA;

            while (slot.plusMinutes(SLOT_MINUTOS).compareTo(HORA_CIERRE) <= 0) {

                boolean ocupado = false;

                // Comprobamos si alguna reserva activa ocupa este slot
                for (Reserva r : reservasDelDia) {
                    if (r.getEstado() == Reserva.Estado.ACTIVA) {
                        // El slot está ocupado si cae dentro del rango [horaInicio, horaFin)
                        if (!slot.isBefore(r.getHoraInicio()) && slot.isBefore(r.getHoraFin())) {
                            ocupado = true;
                            break;
                        }
                    }
                }

                if (!ocupado) {
                    slotsLibres.add(slot);
                }

                slot = slot.plusMinutes(SLOT_MINUTOS);
            }

            String linea = String.format("  Pista '%s' (%s) → %d slots libres: %s",
                    pista.getNombre(), hoy, slotsLibres.size(), slotsLibres);
            lineaBoletín.add(linea);
        }

        // Enviamos el boletín a cada usuario
        List<Usuario> usuarios = new ArrayList<>();
        usuarioRepo.findAll().forEach(usuarios::add);

        for (Usuario usuario : usuarios) {
            log.info("Boletín mensual enviado a {}", usuario.getEmail());
            for (String linea : lineaBoletín) {
                log.info(linea);
            }
        }

        log.info("Tarea programada: boletín mensual completado ({} usuarios notificados, {} pistas revisadas)",
                usuarios.size(), pistas.size());
    }
}
