package tests;

import utilitaire.DBconnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBconnectionTest {

    @Test
    public void testDatabaseConnection() {
        try (Connection connection = DBconnection.getConnection()) {
            assertNotNull(connection, "Connection to database should not be null");
            assertTrue(connection.isValid(0), "Database connection should be valid");
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false, "Failed to establish a connection to the database.");
        }
    }
}
