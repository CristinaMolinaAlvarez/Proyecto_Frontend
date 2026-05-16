package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Rol;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.UsuarioRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsuarioRepo usuarioRepo;
    // Usamos el PasswordEncoder del contexto de Spring (BCrypt, configurado en ConfiguracionSeguridad)
    // para que el hash que guardamos sea el mismo que Spring Security usa para verificar el login
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsuarioRepo usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar usuario
    public Usuario register(Usuario usuario) {
        log.info("Servicio: intentando registrar usuario con email {}", usuario.getEmail());

        // 409 si el email ya existe
        if (usuarioRepo.existsByEmail(usuario.getEmail())) {
            log.error("Servicio: no se puede registrar, el email {} ya existe", usuario.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        usuario.setRol(Rol.USER);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        // BCrypt: compatible con el PasswordEncoder de Spring Security
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario guardado = usuarioRepo.save(usuario);
        log.info("Servicio: usuario registrado correctamente con id {} y email {}", guardado.getIdUsuario(), guardado.getEmail());
        return guardado;
    }

    // Devuelve el usuario autenticado
    public Usuario me(Authentication authentication) {
        log.debug("Servicio: resolviendo usuario autenticado {}", authentication.getName());

        if (authentication == null) {
            log.error("Servicio: acceso a /auth/me sin autenticación");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();

        return usuarioRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.error("Servicio: usuario autenticado {} no encontrado en base de datos", email);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    // GET lista usuarios (ADMIN)
    public Iterable<Usuario> getUsers() {
        log.info("Servicio: devolviendo listado de todos los usuarios");
        return usuarioRepo.findAll();
    }

    // GET usuario por id (ADMIN)
    public Usuario getUser(int userId) {
        log.info("Servicio: buscando usuario con id {}", userId);
        return usuarioRepo.findById(userId)
                .orElseThrow(() -> {
                    log.error("Servicio: usuario con id {} no encontrado", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    // Modificar usuario (ADMIN)
    public Usuario modificarUser(int userId, Usuario usuario) {
        log.info("Servicio: modificando usuario con id {}", userId);

        // 404 si el usuario no existe
        Usuario existente = usuarioRepo.findById(userId)
                .orElseThrow(() -> {
                    log.error("Servicio: usuario con id {} no encontrado para modificar", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        // 409 si el email ya está en uso por otro usuario
        Optional<Usuario> emailExistente = usuarioRepo.findByEmailIgnoreCase(usuario.getEmail());
        if (emailExistente.isPresent() && !emailExistente.get().getIdUsuario().equals(userId)) {
            log.error("Servicio: el email {} ya lo usa otro usuario", usuario.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        existente.setNombre(usuario.getNombre());
        existente.setApellidos(usuario.getApellidos());
        existente.setEmail(usuario.getEmail());
        existente.setTelefono(usuario.getTelefono());

        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        Usuario actualizado = usuarioRepo.save(existente);
        log.info("Servicio: usuario con id {} modificado correctamente", userId);
        return actualizado;
    }
}
