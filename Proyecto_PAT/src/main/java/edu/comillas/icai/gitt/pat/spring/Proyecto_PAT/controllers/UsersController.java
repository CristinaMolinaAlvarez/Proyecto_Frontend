package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.controllers;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.services.UsersService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // Registrar usuario
    @PostMapping("/pistaPadel/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario register(@Valid @RequestBody Usuario usuario) {
        log.info("POST /pistaPadel/auth/register - Solicitud de registro para email: {}", usuario.getEmail());
        Usuario creado = usersService.register(usuario);
        log.info("POST /pistaPadel/auth/register - Usuario registrado correctamente con id: {}", creado.getIdUsuario());
        return creado;
    }

    // Devuelve el usuario autenticado
    @GetMapping("/pistaPadel/auth/me")
    public Usuario me(Authentication authentication) {
        log.info("GET /pistaPadel/auth/me - Consultando usuario autenticado: {}", authentication.getName());
        Usuario usuario = usersService.me(authentication);
        log.info("GET /pistaPadel/auth/me - Devolviendo datos del usuario id: {}", usuario.getIdUsuario());
        return usuario;
    }

    // GET lista usuarios (ADMIN)
    @GetMapping("/pistaPadel/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Usuario> getUsers() {
        log.info("GET /pistaPadel/users - Admin solicita listado de todos los usuarios");
        Iterable<Usuario> usuarios = usersService.getUsers();
        log.info("GET /pistaPadel/users - Listado devuelto correctamente");
        return usuarios;
    }

    // GET usuario por id (ADMIN)
    @GetMapping("/pistaPadel/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario getUser(@PathVariable int userId) {
        log.info("GET /pistaPadel/users/{} - Admin solicita datos del usuario", userId);
        Usuario usuario = usersService.getUser(userId);
        log.info("GET /pistaPadel/users/{} - Usuario encontrado: {}", userId, usuario.getEmail());
        return usuario;
    }

    // Modificar usuario (ADMIN)
    @PatchMapping("/pistaPadel/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario modificarUser(@PathVariable int userId, @Valid @RequestBody Usuario usuario) {
        log.info("PATCH /pistaPadel/users/{} - Admin solicita modificar usuario", userId);
        Usuario actualizado = usersService.modificarUser(userId, usuario);
        log.info("PATCH /pistaPadel/users/{} - Usuario modificado correctamente", userId);
        return actualizado;
    }
}
