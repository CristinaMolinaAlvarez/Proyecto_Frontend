package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends CrudRepository<Usuario, Integer> {

    // Buscar por email para el login (ignora mayúsculas)
    Optional<Usuario> findByEmailIgnoreCase(String email);

    // Comprobar si el email ya existe antes de registrar
    boolean existsByEmail(String email);

}
