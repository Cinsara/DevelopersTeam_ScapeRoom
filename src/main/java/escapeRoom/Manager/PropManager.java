package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.model.GameArea.CluePropFactory.*;
import escapeRoom.model.GameArea.RoomBuilder.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class PropManager {

    private final Connection connection = ConnectionManager.getConnection();
    private PropService propService;
    private InputService inputService;
    private GameElementFactory elementFactory;
    private RoomManager roomManager;

    public PropManager(PropService propService, InputService inputService,
                       GameElementFactory elementFactory) throws SQLException {
        this.propService = propService;
        this.inputService = inputService;
        this.elementFactory = elementFactory;
    }

    public Prop create(int roomId) throws SQLException{

        try {
            GameElementFactory propFactory = ElementsFactoryProducer.getFactory("Prop");

            String type = (inputService.readString(("Enter Type (CLOSET, SPADE, MOUNTAIN): "))).toUpperCase();
            PropType typeEnum = null;

            switch(type) {
                case "CLOSET" -> typeEnum = PropType.CLOSET;
                case "SPADE" -> typeEnum = PropType.SPADE;
                case "MOUNTAIN" -> typeEnum = PropType.MOUNTAIN;
            }

            Prop newProp = (Prop) propFactory.createGameElement(typeEnum,roomId);

            propService.create(newProp);

            return newProp;

        } catch (SQLException e) {
            System.out.println("Error creating Prop: " + e.getMessage());
        }
        return null;
    }

    public Prop createInRoom(PropType type, int roomId) throws SQLException{

        try {
            Prop newProp = (Prop) elementFactory.createGameElement(type,roomId);

            propService.create(newProp);

            return newProp;

        } catch (SQLException e) {
            System.out.println("Error creating Prop: " + e.getMessage());
        }
        return null;
    }

    public void read(int id) throws SQLException {

        try {
            Optional<Prop> PropOpt = propService.read(id);
            if (PropOpt.isEmpty()) {
                System.out.println("Prop not found with ID: " + id);
            } else {
                System.out.println(PropOpt);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving Prop from DB: " + e.getMessage());;
        }

    }

    public void update(int id){

        try {
            Optional<Prop> propOpt = propService.read(id);
            if (propOpt.isEmpty()) {
                System.out.println("Prop not found with ID: " + id);
                return;
            }

            Prop existingProp = propOpt.get();

            System.out.println("\nEnter new values (leave blank to keep current):");

            String newType = (inputService.readString(("Enter new Type (CLOSET, SPADE, MOUNTAIN): "))).toUpperCase();
            ElementType newTypeEnum = null;

            switch(newType){
                case "CLOSET" -> newTypeEnum = PropType.CLOSET;
                case "SPADE" -> newTypeEnum = PropType.SPADE;
                case "MOUNTAIN" -> newTypeEnum = PropType.MOUNTAIN;
            }

            int newRoomId = inputService.readInt("Enter new Room ID for the Clue: ");

            try {
                for (Room room : roomManager.getAllRooms()) {
                    System.out.println(room);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving all the rooms available: " + e.getMessage());
            }

            Prop updatedProp = new Prop(
                    (PropType) (newType.isEmpty() ? existingProp.getType() : newTypeEnum),
                    id,
                    newRoomId==0 ? existingProp.getRoomId() : newRoomId);

            propService.update(updatedProp);
            System.out.println("Prop updated successfully!");
            System.out.println(updatedProp);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }

    }

    public void delete(int id) throws SQLException {

        try {
            Optional<Prop> propOpt = propService.read(id);
            if (propOpt.isEmpty()) {
                System.out.println("Prop not found with ID: " + id);
            } else {
                propService.delete(id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Prop: " + e.getMessage());
        }
    }

}