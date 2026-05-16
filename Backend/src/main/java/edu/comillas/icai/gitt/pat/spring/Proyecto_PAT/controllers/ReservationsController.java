package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.controllers;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.ReservaRequest;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services.ReservationsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationsController {

    private static final Logger log = LoggerFactory.getLogger(ReservationsController.class);

    private final ReservationsService reservationsService;

    public ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    // Crear reserva
    @PostMapping("/pistaPadel/reservations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva crearReserva(Authentication auth, @Valid @RequestBody ReservaRequest reservaRequest) {
        log.info("POST /pistaPadel/reservations - Usuario {} solicita crear reserva en pista {} el {}",
                auth.getName(), reservaRequest.getIdPista(), reservaRequest.getFechaReserva());
        Reserva creada = reservationsService.crearReserva(auth, reservaRequest);
        log.info("POST /pistaPadel/reservations - Reserva creada con id: {}", creada.getIdReserva());
        return creada;
    }

    // Listar reservas
    @GetMapping("/pistaPadel/reservations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Reserva> listarReservas(Authentication auth) {
        log.info("GET /pistaPadel/reservations - Usuario {} solicita sus reservas", auth.getName());
        List<Reserva> reservas = reservationsService.listarReservas(auth);
        log.info("GET /pistaPadel/reservations - Devolviendo {} reservas a {}", reservas.size(), auth.getName());
        return reservas;
    }

    // Obtener una reserva concreta
    @GetMapping("/pistaPadel/reservations/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Reserva getReserva(Authentication auth, @PathVariable int id) {
        log.info("GET /pistaPadel/reservations/{} - Usuario {} solicita detalle de reserva", id, auth.getName());
        Reserva reserva = reservationsService.getReserva(auth, id);
        log.info("GET /pistaPadel/reservations/{} - Reserva encontrada y devuelta", id);
        return reserva;
    }

    // Modificar reserva
    @PatchMapping("/pistaPadel/reservations/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Reserva reprogramarReserva(Authentication auth,
                                      @PathVariable int id,
                                      @Valid @RequestBody ReservaRequest reservaRequest) {
        log.info("PATCH /pistaPadel/reservations/{} - Usuario {} solicita modificar reserva", id, auth.getName());
        Reserva modificada = reservationsService.reprogramarReserva(auth, id, reservaRequest);
        log.info("PATCH /pistaPadel/reservations/{} - Reserva modificada correctamente", id);
        return modificada;
    }

    // Cancelar reserva
    @DeleteMapping("/pistaPadel/reservations/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarReserva(Authentication auth, @PathVariable int id) {
        log.info("DELETE /pistaPadel/reservations/{} - Usuario {} solicita cancelar reserva", id, auth.getName());
        reservationsService.cancelarReserva(auth, id);
        log.info("DELETE /pistaPadel/reservations/{} - Reserva cancelada correctamente", id);
    }
}
