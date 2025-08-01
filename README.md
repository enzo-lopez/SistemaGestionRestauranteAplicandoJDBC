# Reserva Restaurante

Este proyecto es una aplicación de gestión de reservas para restaurantes. Permite a los usuarios crear, cancelar y listar reservas, así como gestionar la persistencia de datos a través de una base de datos.

## Estructura del Proyecto

El proyecto está organizado en varias capas:

- **Presentación**: Contiene la interfaz de usuario y el punto de entrada de la aplicación.
  - `RestauranteApp.java`: Clase principal que inicia la aplicación.

- **Negocio**: Contiene la lógica de negocio relacionada con las reservas.
  - `ReservaService.java`: Clase que maneja las operaciones de reservas como crear, cancelar y listar.

- **Persistencia**: Se encarga de la interacción con la base de datos.
  - `ReservaDAO.java`: Clase que realiza operaciones CRUD sobre las reservas a traves de JDBC y MySQL.

- **Modelo**: Define las entidades del dominio.
  - `Reserva.java`: Clase que representa una reserva con sus propiedades.

## Requisitos

- Java 11 o superior
- Maven