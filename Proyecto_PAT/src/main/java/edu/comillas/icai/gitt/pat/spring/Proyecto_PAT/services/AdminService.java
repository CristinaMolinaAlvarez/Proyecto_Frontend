package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.ReservaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final ReservaRepo reservaRepo;

    public AdminService(ReservaRepo reservaRepo) {
        this.reservaRepo = reservaRepo;
    }

    // (ADMIN) Ver todas las reservas con filtros opcionales
    public List<Reserva> getAllReservations(String date, Integer courtId, Integer userId) {
        log.info("Servicio Admin: consultando reservas con filtros date={}, courtId={}, userId={}", date, courtId, userId);

        List<Reserva> reservas = new ArrayList<>();
        reservaRepo.findAll().forEach(reservas::add);
        log.debug("Servicio Admin: total reservas en sistema antes de filtrar: {}", reservas.size());

        // Filtro por fecha
        if (date != null) {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                List<Reserva> filtradas = new ArrayList<>();
                for (Reserva r : reservas) {
                    if (r.getFechaReserva().equals(parsedDate)) {
                        filtradas.add(r);
                    }
                }
                reservas = filtradas;
                log.debug("Servicio Admin: tras filtrar por fecha {}: {} reservas", parsedDate, reservas.size());
            } catch (Exception e) {
                log.error("Servicio Admin: formato de fecha incorrecto '{}'", date);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        // Filtro por pista
        if (courtId != null) {
            List<Reserva> filtradas = new ArrayList<>();
            for (Reserva r : reservas) {
                if (r.getPista().getIdPista().equals(courtId)) {
                    filtradas.add(r);
                }
            }
            reservas = filtradas;
            log.debug("Servicio Admin: tras filtrar por pista {}: {} reservas", courtId, reservas.size());
        }

        // Filtro por usuario
        if (userId != null) {
            List<Reserva> filtradas = new ArrayList<>();
            for (Reserva r : reservas) {
                if (r.getUsuario().getIdUsuario().equals(userId)) {
                    filtradas.add(r);
                }
            }
            reservas = filtradas;
            log.debug("Servicio Admin: tras filtrar por usuario {}: {} reservas", userId, reservas.size());
        }

        log.info("Servicio Admin: devolviendo {} reservas tras aplicar filtros", reservas.size());
        return reservas;
    }
}
