package edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.repos;

import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Pista;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Reserva;
import edu.comillas.icai.gitt.pat.spring.Proyecto_PAT.modelos.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepo extends CrudRepository<Reserva, Integer> {

    // Reservas de un usuario concreto
    List<Reserva> findByUsuario(Usuario usuario);

    // Reservas de una pista concreta
    List<Reserva> findByPista(Pista pista);

    // Reservas de una pista en un día (para calcular disponibilidad)
    List<Reserva> findByPistaAndFechaReserva(Pista pista, LocalDate fechaReserva);

    // Reservas de un usuario en un día
    List<Reserva> findByUsuarioAndFechaReserva(Usuario usuario, LocalDate fechaReserva);

    // Reservas por idUsuario (útil para filtros sin cargar entidad)
    List<Reserva> findByUsuario_IdUsuario(Integer idUsuario);

    // Reservas por idPista (útil para filtros sin cargar entidad)
    List<Reserva> findByPista_IdPista(Integer idPista);

    // Reservas de una pista en una fecha (por ids, evita cargar entidades)
    List<Reserva> findByPista_IdPistaAndFechaReserva(Integer idPista, LocalDate fecha);

    // Reservas de una fecha concreta (para el recordatorio diario)
    List<Reserva> findByFechaReserva(LocalDate fechaReserva);

}
