package persistencia.dao;

import modelo.Reserva;

import java.util.List;

public interface IReservaDao {

    public Reserva guardarReserva(Reserva reserva) throws Exception;
    public List<Reserva> listarReservas() throws Exception;
    public List<Reserva> buscarReservaPorNombre(String nombre) throws Exception;
    public void eliminarReserva(Long id) throws Exception;
}
