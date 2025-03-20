package escapeRoom.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PropServiceTest {
    static PropService service;

    @BeforeAll
    static void setUp(){
        service = new PropService();
    }

    @Test
    void testGetAllWorks() {
        assertDoesNotThrow(()->service.getAll());
    }

    @Test
    void testGetAll(){
        try (ResultSet rs = service.getAll();) {
          do{
              System.out.println(rs.getString("prop_type"));
          }while(rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}