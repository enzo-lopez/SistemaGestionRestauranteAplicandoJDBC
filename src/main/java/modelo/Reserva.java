package modelo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCliente;
    private String fecha;
    private int numeroPersonas;

    public Reserva(){}

    public Reserva(String nombreCliente, String fecha, int numeroPersonas) {
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.numeroPersonas = numeroPersonas;
    }

}