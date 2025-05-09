package escapeRoom.Controller.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.*;
import escapeRoom.Model.GameArea.RoomBuilder.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropManager {

    private final Connection connection = ConnectionManager.getConnection();
    private PropService propService;
    private InputService inputService;
    private GameElementFactory elementFactory;
    private RoomService roomService;


    public PropManager(InputService inputService, PropService propService,RoomService roomService) throws SQLException {
        this.propService = propService;
        this.inputService = inputService;
        this.roomService = roomService;
        this.elementFactory = new PropFactory();
    }

    public PropManager() throws SQLException {};

    public Prop create() throws SQLException, BackToSecondaryMenuException {


        String opc = "yes";
        do {
            try {
                GameElementFactory propFactory = ElementsFactoryProducer.getFactory("Prop");

                String type = (inputService.readString(("Enter Type (CLOSET, SPADE, MOUNTAIN): "))).toUpperCase();
                PropType typeEnum = null;

                switch (type) {
                    case "CLOSET" -> typeEnum = PropType.CLOSET;
                    case "SPADE" -> typeEnum = PropType.SPADE;
                    case "MOUNTAIN" -> typeEnum = PropType.MOUNTAIN;
                }

                System.out.println("***AVAILABLE ROOMS***");

                try {
                    for (Room room : roomService.getAllEntities(connection)) {
                        roomService.read(room.getId()).ifPresent(System.out::println);
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving all the rooms available: " + e.getMessage());
                }

                int newRoomId = inputService.readInt("Enter new Room ID for the Clue: ");


                Prop newProp = (Prop) propFactory.createGameElement(typeEnum, newRoomId);

                propService.create(newProp);

                System.out.println("Here's your new Prop: \n" + newProp);

            } catch (SQLException e) {
                System.out.println("Error creating Prop: " + e.getMessage());
            }
            opc = inputService.readString("Do you want to create another one? yes/no");

        } while (!opc.equals("no"));

        return null;
    }

    public Prop createInNewRoom(PropType type, int roomId) throws SQLException{

        try {
            Prop newProp = (Prop) elementFactory.createGameElement(type,roomId);

            propService.create(newProp);

            return newProp;

        } catch (SQLException e) {
            System.out.println("Error creating Prop: " + e.getMessage());
        }
        return null;
    }

    public List<Prop> addPropsToRoom(int roomId) throws SQLException,BackToSecondaryMenuException{


        List<Prop> props = new ArrayList<>();
        String opc;
        do {
            try {
                GameElementFactory propFactory = ElementsFactoryProducer.getFactory("Prop");

                String type = (inputService.readString(("Enter Type (CLOSET, SPADE, MOUNTAIN): "))).toUpperCase();

                while (!type.equals("CLOSET") && !type.equals("SPADE") && !type.equals("MOUNTAIN")) {
                    type = (inputService.readString(("Please, enter only CLOSET, SPADE or MOUNTAIN: "))).toUpperCase();
                }

                PropType typeEnum = null;

                switch (type) {
                    case "CLOSET" -> typeEnum = PropType.CLOSET;
                    case "SPADE" -> typeEnum = PropType.SPADE;
                    case "MOUNTAIN" -> typeEnum = PropType.MOUNTAIN;
                }

                Prop newProp = (Prop) propFactory.createGameElement(typeEnum, roomId);
                propService.create(newProp);
                props.add(newProp);

            } catch (SQLException e) {
                System.out.println("Error creating Prop: " + e.getMessage());
            }
            opc = inputService.readString("Do you want to create another one? yes/no");

        } while (!opc.equalsIgnoreCase("no"));

        return props;
    }

    public List<Prop> getAllProps() throws SQLException {
        List<Prop> props = propService.getAllEntities(connection);
        return props;
    }

    public Optional<Prop> read(int id) throws SQLException {

        try {
            Optional<Prop> PropOpt = propService.read(id);
            if (PropOpt.isEmpty()) {
                System.out.println("Prop with ID: " + id + "not found.");
            } else {
                PropOpt.ifPresent(System.out::println);
                return PropOpt;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving Prop from DB: " + e.getMessage());
        }
        return null;
    }

    public void update() throws SQLException,BackToSecondaryMenuException {


        getAllProps();

        int propId = inputService.readInt("Which Prop do you want to update? (Enter the ID)");

        try {
            Optional<Prop> propOpt = propService.read(propId);
            if (propOpt.isEmpty()) {
                System.out.println("Prop not found with ID: " + propId);
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
            System.out.println();
            System.out.println("***AVAILABLE ROOMS***");

            try {
                for (Room room : roomService.getAllEntities(connection)) {
                    roomService.read(room.getId()).ifPresent(System.out::println);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving all the rooms available: " + e.getMessage());
            }

            int newRoomId = inputService.readInt("Enter new Room ID for the Clue: ");


            Prop updatedProp = new Prop(
                    (PropType) (newType.isEmpty() ? existingProp.getType() : newTypeEnum),
                    propId,
                    newRoomId==0 ? existingProp.getRoomId() : newRoomId);

            propService.update(updatedProp);
            System.out.println("Prop updated successfully!");
            System.out.println(updatedProp);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }

    }

    public void delete() throws SQLException,BackToSecondaryMenuException {

        getAllProps();

        int propId = inputService.readInt("Which Prop do you want to update? (Enter the ID)");

        try {
            Optional<Prop> propOpt = propService.read(propId);
            if (propOpt.isEmpty()) {
                System.out.println("Prop not found with ID: " + propId);
            } else {
                propService.delete(propId);
                System.out.println("Prop with ID " + propId + "deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Prop: " + e.getMessage());
        }
    }

    public void removePropFromRoom(int roomId) throws SQLException,BackToSecondaryMenuException{

        String opc;
        do {
            try {
                Optional<Room> optRoom = null;
                try {
                    optRoom = roomService.read(roomId);
                } catch (SQLException e) {
                    System.out.println("Error retrieving Room " + roomId + ": " + e.getMessage());
                }

                System.out.println("This Room has the following Props: ");

                List<Integer> listProps = new ArrayList<>(optRoom.get().getProps_id());

                for (Integer prop : listProps) {
                    read(prop);
                }

                int propIdToRemove = inputService.readInt("Which one do you want to remove? (Enter the ID)");

                propService.delete(propIdToRemove);

                System.out.println("Prop with ID " + propIdToRemove + " has been removed." );


            } catch (SQLException e) {
                System.out.println("Error removing Prop: " + e.getMessage());
            }
            opc = inputService.readString("Do you want to remove another one? yes/no");

        } while (!opc.equals("no"));

    }

}