package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.controllers;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services.CourtsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourtsController {

    private static final Logger log = LoggerFactory.getLogger(CourtsController.class);

    private final CourtsService courtsService;

    public CourtsController(CourtsService courtsService) {
        this.courtsService = courtsService;
    }

    // Crear pista
    @PostMapping("/pistaPadel/courts")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Pista crearCourt(@Valid @RequestBody Pista pista) {
        log.info("POST /pistaPadel/courts - Admin solicita crear pista: {}", pista.getNombre());
        Pista creada = courtsService.crearCourt(pista);
        log.info("POST /pistaPadel/courts - Pista creada con id: {}", creada.getIdPista());
        return creada;
    }

    // Obtener lista de pistas
    @GetMapping("/pistaPadel/courts")
    public Iterable<Pista> getCourts() {
        log.info("GET /pistaPadel/courts - Solicitud de listado de pistas");
        Iterable<Pista> pistas = courtsService.getCourts();
        log.info("GET /pistaPadel/courts - Listado de pistas devuelto correctamente");
        return pistas;
    }

    // Obtener una pista por id
    @GetMapping("/pistaPadel/courts/{courtId}")
    public Pista getCourt(@PathVariable int courtId) {
        log.info("GET /pistaPadel/courts/{} - Solicitud de detalle de pista", courtId);
        Pista pista = courtsService.getCourt(courtId);
        log.info("GET /pistaPadel/courts/{} - Pista encontrada: {}", courtId, pista.getNombre());
        return pista;
    }

    // Modificar pista
    @PatchMapping("/pistaPadel/courts/{courtId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Pista modificarCourt(@PathVariable int courtId, @Valid @RequestBody Pista pista) {
        log.info("PATCH /pistaPadel/courts/{} - Admin solicita modificar pista", courtId);
        Pista modificada = courtsService.modificarCourt(courtId, pista);
        log.info("PATCH /pistaPadel/courts/{} - Pista modificada correctamente", courtId);
        return modificada;
    }

    // Borrar pista
    @DeleteMapping("/pistaPadel/courts/{courtId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCourt(@PathVariable int courtId) {
        log.info("DELETE /pistaPadel/courts/{} - Admin solicita borrar pista", courtId);
        courtsService.borrarCourt(courtId);
        log.info("DELETE /pistaPadel/courts/{} - Pista borrada correctamente", courtId);
    }
}
