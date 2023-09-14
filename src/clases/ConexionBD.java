package clases;

import java.sql.*;

public class ConexionBD {
    private final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private final String usuario = "root";
    private final String contraseña = "1234";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    // insertar una nueva reserva en la tabla Reservas
    public int insertarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reservas (fechaEntrada, fechaSalida, valor, formaPago) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, reserva.getFechaEntrada());
            statement.setDate(2, reserva.getFechaSalida());
            statement.setDouble(3, reserva.getValor());
            statement.setString(4, reserva.getFormaPago());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el ID generado para la reserva
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // insertar un nuevo huésped en la tabla Huéspedes
    public boolean insertarHuesped(Huesped huesped) {
        String sql = "INSERT INTO Huespedes (nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setDate(3, huesped.getFechaNacimiento());
            statement.setString(4, huesped.getNacionalidad());
            statement.setString(5, huesped.getTelefono());
            statement.setInt(6, huesped.getIdReserva());

            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
