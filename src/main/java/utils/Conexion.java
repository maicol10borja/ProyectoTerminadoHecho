package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Declaramos e inicializamos tres parametros
    //Para la conexión a la base de datos
    private static String url="jdbc:mysql://localhost:3306/carro?useTimezone=true&serverTimezone=UTC";
    private static String username="root";
    private static String password="";
    //Implementamos un método para obtener la
    //conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
}
