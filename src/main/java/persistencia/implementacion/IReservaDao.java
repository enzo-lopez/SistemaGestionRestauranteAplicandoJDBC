package persistencia.implementacion;

import modelo.Reserva;

import java.sql.SQLException;
import java.util.List;

public interface IReservaDao {

    public Reserva guardarReserva(Reserva reserva) throws SQLException;
    public List<Reserva> listarReservas() throws SQLException;
    public List<Reserva> buscarReservaPorNombre(String nombre) throws SQLException;
    public void eliminarReserva(Long id) throws SQLException;
}
