package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Rol;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.PistaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.ReservaRepo;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos.UsuarioRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Inserta datos de ejemplo al arrancar la aplicación.
 * Solo se ejecuta si la base de datos está vacía.
 *
 * Usuarios de prueba:
 *   admin@padelpoint.com  /  admin123    (ADMIN)
 *   ana@padelpoint.com    /  password123 (USER)
 *   carlos@padelpoint.com /  password123 (USER)
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final PistaRepo pistaRepo;
    private final UsuarioRepo usuarioRepo;
    private final ReservaRepo reservaRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PistaRepo pistaRepo, UsuarioRepo usuarioRepo,
                           ReservaRepo reservaRepo, PasswordEncoder passwordEncoder) {
        this.pistaRepo       = pistaRepo;
        this.usuarioRepo     = usuarioRepo;
        this.reservaRepo     = reservaRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Solo insertamos si la BD está vacía
        if (pistaRepo.count() > 0) {
            log.info("DataInitializer: la base de datos ya tiene datos, omitiendo carga inicial");
            return;
        }

        log.info("DataInitializer: insertando datos de ejemplo...");

        // Pistas
        Pista p1 = new Pista();
        p1.setNombre("Central Indoor");
        p1.setUbicacion("Interior");
        p1.setPrecioHora(18.0);
        p1.setActiva(true);
        p1.setFechaAlta(LocalDateTime.now());
        pistaRepo.save(p1);

        Pista p2 = new Pista();
        p2.setNombre("Jardín");
        p2.setUbicacion("Exterior");
        p2.setPrecioHora(16.0);
        p2.setActiva(true);
        p2.setFechaAlta(LocalDateTime.now());
        pistaRepo.save(p2);

        Pista p3 = new Pista();
        p3.setNombre("Lima Pro");
        p3.setUbicacion("Interior");
        p3.setPrecioHora(20.0);
        p3.setActiva(true);
        p3.setFechaAlta(LocalDateTime.now());
        pistaRepo.save(p3);

        log.info("DataInitializer: 3 pistas insertadas");

        // Usuarios
        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setApellidos("PádelPoint");
        admin.setEmail("admin@padelpoint.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setTelefono("600000001");
        admin.setRol(Rol.ADMIN);
        admin.setFechaRegistro(LocalDateTime.now());
        admin.setActivo(true);
        usuarioRepo.save(admin);

        Usuario ana = new Usuario();
        ana.setNombre("Ana");
        ana.setApellidos("García López");
        ana.setEmail("ana@padelpoint.com");
        ana.setPassword(passwordEncoder.encode("password123"));
        ana.setTelefono("612345678");
        ana.setRol(Rol.USER);
        ana.setFechaRegistro(LocalDateTime.now());
        ana.setActivo(true);
        usuarioRepo.save(ana);

        Usuario carlos = new Usuario();
        carlos.setNombre("Carlos");
        carlos.setApellidos("Martínez Ruiz");
        carlos.setEmail("carlos@padelpoint.com");
        carlos.setPassword(passwordEncoder.encode("password123"));
        carlos.setTelefono("698765432");
        carlos.setRol(Rol.USER);
        carlos.setFechaRegistro(LocalDateTime.now());
        carlos.setActivo(true);
        usuarioRepo.save(carlos);

        log.info("DataInitializer: 3 usuarios insertados (1 admin, 2 users)");

        // Reservas
        LocalDate hoy = LocalDate.now();

        Reserva r1 = new Reserva();
        r1.setUsuario(ana);
        r1.setPista(p1);
        r1.setFechaReserva(hoy);
        r1.setHoraInicio(LocalTime.of(10, 0));
        r1.setDuracionMinutos(60);
        r1.setHoraFin(LocalTime.of(11, 0));
        r1.setEstado(Reserva.Estado.ACTIVA);
        r1.setFechaCreacion(LocalDateTime.now());
        reservaRepo.save(r1);

        Reserva r2 = new Reserva();
        r2.setUsuario(carlos);
        r2.setPista(p2);
        r2.setFechaReserva(hoy);
        r2.setHoraInicio(LocalTime.of(18, 0));
        r2.setDuracionMinutos(120);
        r2.setHoraFin(LocalTime.of(20, 0));
        r2.setEstado(Reserva.Estado.ACTIVA);
        r2.setFechaCreacion(LocalDateTime.now());
        reservaRepo.save(r2);

        Reserva r3 = new Reserva();
        r3.setUsuario(ana);
        r3.setPista(p3);
        r3.setFechaReserva(hoy.minusDays(3));
        r3.setHoraInicio(LocalTime.of(17, 0));
        r3.setDuracionMinutos(60);
        r3.setHoraFin(LocalTime.of(18, 0));
        r3.setEstado(Reserva.Estado.CANCELADA);
        r3.setFechaCreacion(LocalDateTime.now().minusDays(5));
        reservaRepo.save(r3);

        log.info("DataInitializer: 3 reservas de ejemplo insertadas (2 activas, 1 cancelada)");
        log.info("DataInitializer: carga inicial completada correctamente");
    }
}
