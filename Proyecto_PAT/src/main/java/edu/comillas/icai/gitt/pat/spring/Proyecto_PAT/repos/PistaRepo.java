package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PistaRepo extends CrudRepository<Pista, Integer> {

    // Pistas activas
    List<Pista> findByActivaTrue();

}
