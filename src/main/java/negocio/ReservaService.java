package negocio;

import modelo.Reserva;
import persistencia.dao.ReservaDaoImp;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import excepciones.ReservaException;

public class ReservaService {
    private ReservaDaoImp reservaRepository;
    private static final int MAX_PERSONAS = 8;
    private static final int HORA_APERTURA = 12;
    private static final int HORA_CIERRE = 23;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ReservaService(ReservaDaoImp reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(Reserva reserva) throws SQLException {
        // Validar cantidad de personas
        if (reserva.getNumeroPersonas() < 1 || reserva.getNumeroPersonas() > MAX_PERSONAS) {
            throw new ReservaException("La reserva debe ser para entre 1 y 8 personas.");
        }

        // Validar formato y rango de fecha/hora
        LocalDateTime fechaHora;
        try {
            fechaHora = LocalDateTime.parse(reserva.getFecha(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ReservaException("Formato de fecha y hora invalido. Use dd/MM/yyyy HH:mm");
        }

        // Validar que no sea una fecha pasada
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new ReservaException("No se pueden hacer reservas para fechas pasadas.");
        }

        // Validar horario de atención
        int hora = fechaHora.getHour();
        if (hora < HORA_APERTURA || hora >= HORA_CIERRE) {
            throw new ReservaException("El horario de atencion es de 12:00 a 23:00.");
        }

        // Validar duplicados (mismo cliente, misma fecha/hora)
        boolean duplicada = reservaRepository.listarReservas().stream()
                .anyMatch(r -> r.getNombreCliente().equalsIgnoreCase(reserva.getNombreCliente())
                        && r.getFecha().equals(reserva.getFecha()));
        if (duplicada) {
            throw new ReservaException("Ya existe una reserva para ese cliente en esa fecha y hora.");
        }

        return reservaRepository.guardarReserva(reserva);
    }

    public void cancelarReserva(Long id) throws SQLException {
        reservaRepository.eliminarReserva(id);
    }

    public List<Reserva> listarReservas() throws SQLException {
        return reservaRepository.listarReservas();
    }

    public List<Reserva> buscarReserva(String nombre) throws SQLException {
        return reservaRepository.buscarReservaPorNombre(nombre);
    }
}