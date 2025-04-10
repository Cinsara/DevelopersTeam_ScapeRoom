package escapeRoom.connectionManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionManagerTest {

    static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    @Test
    public void testGetConnection() throws SQLException {
        assertNotNull(ConnectionManager.getConnection());
    }
    @Test
    public void testUniqueConnection() throws SQLException {
        assertEquals(connection, ConnectionManager.getConnection());
    }
}