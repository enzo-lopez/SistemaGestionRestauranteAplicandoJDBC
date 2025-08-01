package persistencia.dao;

import modelo.Reserva;
import persistencia.implementacion.IReservaDao;
import persistencia.utils.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDaoImp implements IReservaDao {

    /*
    Connection conexion;

    public ReservaDaoImp() throws SQLException {
        this.conexion = ConexionMySQL.obtenerConexion();
    }*/

    @Override
    public Reserva guardarReserva(Reserva reserva) throws SQLException {
            Reserva nuevaReserva = null;
            String sql = "INSERT INTO reserva (nombreCliente, fecha, numeroPersonas) VALUES (?, ?, ?)";

        try(Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, reserva.getNombreCliente());
            ps.setString(2, reserva.getFecha());
            ps.setInt(3, reserva.getNumeroPersonas());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                reserva.setId(rs.getLong(1));
                nuevaReserva = reserva;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return nuevaReserva;
    }

    @Override
    public List<Reserva> listarReservas() throws SQLException {

        List<Reserva> reservas = new ArrayList<Reserva>();
        String sql = "SELECT * FROM reserva";

        try(Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getLong("id"));
                reserva.setNombreCliente(rs.getString("nombreCliente"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setNumeroPersonas(rs.getInt("numeroPersonas"));
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    @Override
    public List<Reserva> buscarReservaPorNombre(String nombre) throws SQLException {

        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE nombreCliente = ?";

        try(Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getLong("id"));
                reserva.setNombreCliente(rs.getString("nombreCliente"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setNumeroPersonas(rs.getInt("numeroPersonas"));
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    @Override
    public void eliminarReserva(Long id) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try(Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}