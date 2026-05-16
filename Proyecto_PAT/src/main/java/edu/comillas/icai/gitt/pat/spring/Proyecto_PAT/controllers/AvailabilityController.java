package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.controllers;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Disponibilidad;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services.AvailabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// Calcula qué horas están libres en una pista para un día
@RestController
public class AvailabilityController {

    private static final Logger log = LoggerFactory.getLogger(AvailabilityController.class);

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    // GET /pistaPadel/availability?date=...&courtId=...
    @GetMapping("/pistaPadel/availability")
    public Object availability(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer courtId
    ) {
        log.info("GET /pistaPadel/availability - Consultando disponibilidad para fecha={} courtId={}",
                date, courtId != null ? courtId : "todas");
        Object resultado = availabilityService.availability(date, courtId);
        log.info("GET /pistaPadel/availability - Disponibilidad calculada correctamente");
        return resultado;
    }

    // GET /pistaPadel/courts/{courtId}/availability?date=...
    @GetMapping("/pistaPadel/courts/{courtId}/availability")
    public Disponibilidad availabilityCourt(
            @PathVariable int courtId,
            @RequestParam(required = false) String date
    ) {
        log.info("GET /pistaPadel/courts/{}/availability - Consultando disponibilidad para fecha={}",
                courtId, date);
        Disponibilidad disponibilidad = availabilityService.availabilityCourt(courtId, date);
        log.info("GET /pistaPadel/courts/{}/availability - Devueltos {} slots libres",
                courtId, disponibilidad.franjasDisponibles().size());
        return disponibilidad;
    }
}
