package presentacion;

import modelo.Reserva;
import negocio.ReservaService;
import persistencia.dao.implementacion.ReservaDaoImpHibernate;
import persistencia.dao.implementacion.ReservaDaoImpJDBC;
import excepciones.ReservaException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RestauranteApp {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        //ReservaService reservaService = new ReservaService(new ReservaDaoImpJDBC());
        ReservaService reservaService = new ReservaService(new ReservaDaoImpHibernate());
        int opcion;

        do {
            System.out.println("\n--- SISTEMA DE RESERVAS ---");
            System.out.println("1. Registrar nueva reserva");
            System.out.println("2. Listar reservas");
            System.out.println("3. Buscar reservas por cliente");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Salir");
            System.out.print("Seleccione: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            try {
                switch (opcion) {
                    case 1:
                        System.out.print("Nombre del cliente: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Número de personas: ");
                        int personas = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Fecha y hora (dd/MM/yyyy HH:mm): ");
                        String fecha = scanner.nextLine();

                        Reserva nuevaReserva = new Reserva(nombre, fecha, personas);
                        Reserva reservaGuardada = reservaService.crearReserva(nuevaReserva);

                        if (reservaGuardada != null) {
                            System.out.println("Reserva registrada con éxito. ID: " + reservaGuardada.getId());
                        } else {
                            System.out.println("No se pudo registrar la reserva.");
                        }
                        break;
                    case 2:
                        List<Reserva> reservas = reservaService.listarReservas();
                        if (reservas.isEmpty()) {
                            System.out.println("No hay reservas registradas.");
                        } else {
                            reservas.forEach(r -> System.out.println(
                                    "ID: " + r.getId() +
                                    " | Cliente: " + r.getNombreCliente() +
                                    " | Fecha: " + r.getFecha() +
                                    " | Personas: " + r.getNumeroPersonas()
                            ));
                        }
                        break;
                    case 3:
                        System.out.print("Nombre del cliente: ");
                        String cliente = scanner.nextLine();
                        List<Reserva> reservasCliente = reservaService.buscarReserva(cliente);
                        if (reservasCliente.isEmpty()) {
                            System.out.println("No se encontraron reservas para el cliente.");
                        } else {
                            reservasCliente.forEach(r -> System.out.println(
                                    "ID: " + r.getId() +
                                    " | Fecha: " + r.getFecha() +
                                    " | Personas: " + r.getNumeroPersonas()
                            ));
                        }
                        break;
                    case 4:
                        System.out.print("ID de la reserva a cancelar: ");
                        Long idCancelar = scanner.nextLong();
                        scanner.nextLine();
                        Optional<Reserva> reservaOpt = reservaService.listarReservas().stream()
                                .filter(r -> idCancelar.equals(r.getId()))
                                .findFirst();
                        if (reservaOpt.isPresent()) {
                            reservaService.cancelarReserva(idCancelar);
                            System.out.println("Reserva cancelada.");
                        } else {
                            System.out.println("No se encontró la reserva.");
                        }
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (ReservaException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Error inesperado: " + ex.getMessage());
            }
        } while (opcion != 5);

        scanner.close();
    }
}