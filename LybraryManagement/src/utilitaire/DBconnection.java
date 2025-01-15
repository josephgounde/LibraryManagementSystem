package utilitaire;

import java.sql.*;

public class DBconnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioBD";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
