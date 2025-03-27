package escapeRoom.Service.forTesting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import escapeRoom.Service.forTesting.Prop;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PropServiceTest {

    static PropService service;

    @BeforeAll
    static void setUp() throws SQLException {
        service = new PropService();
    }

    @Test
    void testGetAllWorks() {
        assertDoesNotThrow(()->service.getAll(service.getConnection()));
    }

    @Test
    void testGetAll(){
        List<String> expectedProps= List.of("Spade","Closet","Mountain","Spade","Closet","Closet");
        List<Integer> expectedValue = List.of(2,1,3,1,10,10);

        try (ResultSet rs = service.getAll(service.getConnection())) {
            int index = 0;
            while(rs.next()){
                assertEquals(rs.getString("prop_type"),expectedProps.get(index));
                assertEquals(rs.getInt(3),expectedValue.get(index));
                index++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " : " +  e.getCause() + " : " + e.getSQLState());
        }
    }

    @Test
    void create() throws SQLException {
        Prop newProp = new Prop("Closet",10,1);
        Prop returnedProp = service.create(newProp);
        newProp.setId(returnedProp.getId());
        assertEquals(newProp,returnedProp);
    }

    @Test
    void read() throws SQLException {
        Prop newProp = new Prop(1,"Spade",2,1);
        Optional<Prop> optional = service.read(1);
        assertTrue(optional.isPresent());
        optional.ifPresent(prop -> assertEquals(newProp, prop));

    }

    @Test
    void update() throws SQLException {
        Prop newProp = new Prop(1,"Closet",5,1);
        service.update(newProp);
        Optional<Prop> optional = service.read(1);
        assertTrue(optional.isPresent());
        optional.ifPresent(prop -> assertEquals(newProp, prop));
    }

    @Test
    void delete() throws SQLException {
        assertTrue(service.delete(1));
        ResultSet rs = service.getAll(service.getConnection());
        if (rs.next()){
            Prop firstProp = new Prop (rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
            Prop testProp = new Prop (2,"Closet",1,2);
            assertEquals(testProp,firstProp);
        }

    }
}