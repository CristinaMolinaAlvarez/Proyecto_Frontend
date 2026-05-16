package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.controllers;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Permite que un ADMIN vea todas las reservas del sistema con filtros opcionales
@RestController
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // (ADMIN) Ver todas las reservas con filtros opcionales
    @GetMapping("/pistaPadel/admin/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Reserva> getAllReservations(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer courtId,
            @RequestParam(required = false) Integer userId
    ) {
        log.info("GET /pistaPadel/admin/reservations - Admin solicita reservas con filtros: date={}, courtId={}, userId={}",
                date, courtId, userId);
        List<Reserva> reservas = adminService.getAllReservations(date, courtId, userId);
        log.info("GET /pistaPadel/admin/reservations - Devolviendo {} reservas", reservas.size());
        return reservas;
    }
}
