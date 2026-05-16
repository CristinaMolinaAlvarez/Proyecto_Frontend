package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.PistaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class CourtsService {

    private static final Logger log = LoggerFactory.getLogger(CourtsService.class);

    private final PistaRepo pistaRepo;

    public CourtsService(PistaRepo pistaRepo) {
        this.pistaRepo = pistaRepo;
    }

    // Crear pista
    public Pista crearCourt(Pista pista) {
        log.info("Servicio: creando pista con nombre '{}'", pista.getNombre());

        // 409 si la pista ya existe (solo cuando viene id fijo)
        if (pista.getIdPista() != null && pistaRepo.existsById(pista.getIdPista())) {
            log.error("Servicio: ya existe una pista con id {}", pista.getIdPista());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        pista.setFechaAlta(LocalDateTime.now());
        Pista creada = pistaRepo.save(pista);
        log.info("Servicio: pista '{}' creada con id {}", creada.getNombre(), creada.getIdPista());
        return creada;
    }

    // Obtener lista de pistas
    public Iterable<Pista> getCourts() {
        log.info("Servicio: obteniendo listado de todas las pistas");
        return pistaRepo.findAll();
    }

    // Obtener una pista por id
    public Pista getCourt(int courtId) {
        log.info("Servicio: buscando pista con id {}", courtId);
        return pistaRepo.findById(courtId)
                .orElseThrow(() -> {
                    log.error("Servicio: pista con id {} no encontrada", courtId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    // Modificar pista
    public Pista modificarCourt(int courtId, Pista pista) {
        log.info("Servicio: modificando pista con id {}", courtId);

        if (!pistaRepo.existsById(courtId)) {
            log.error("Servicio: pista con id {} no encontrada para modificar", courtId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        pista.setIdPista(courtId);
        Pista modificada = pistaRepo.save(pista);
        log.info("Servicio: pista con id {} modificada correctamente", courtId);
        return modificada;
    }

    // Borrar pista
    public void borrarCourt(int courtId) {
        log.info("Servicio: borrando pista con id {}", courtId);

        if (!pistaRepo.existsById(courtId)) {
            log.error("Servicio: pista con id {} no encontrada para borrar", courtId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        pistaRepo.deleteById(courtId);
        log.info("Servicio: pista con id {} borrada correctamente", courtId);
    }
}
