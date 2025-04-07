package escapeRoom.Controller.InventoryController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.Prop;
import escapeRoom.Model.GameArea.RoomBuilder.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private RoomService roomService;
    private PropService propService;
    private ClueService clueService;

    public InventoryController(RoomService roomService,PropService propService,ClueService clueService){
        this.roomService = roomService;
        this.propService = propService;
        this.clueService = clueService;
    }
    private class RoomWrapper{
        List<Prop> props;
        List<Clue> clues;
        Room room;
        void describeRoom(){
            StringBuilder description = new StringBuilder();
            description.append("----------------------------\n")
                    .append("Room ").append(room.getName()).append(" ID: ").append(room.getId()).append("\n")
                    .append(clues.size()).append(" clues:\n");
            clues.forEach(clue -> {
                description.append("- ").append(clue.getType()).append("\n");
            });
            description.append(props.size()).append( " props:\n");
            props.forEach(prop -> {
                description.append("- ").append(prop.getType()).append(" value ").append(prop.getValue()).append("\n");
            });
            description.append("---Total Room Value: ").append(props.stream().map(Prop::getValue).reduce(0, Integer::sum)).append("\n");
            System.out.print(description);
        }
    }

    public void showInventory(){
        try{
            List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
            List<Prop> props = propService.getAllEntities(ConnectionManager.getConnection());
            List<Clue> clues = clueService.getAllEntities(ConnectionManager.getConnection());
            printInventoryHeader(rooms,props);
            List<RoomWrapper> roomWrappers = wrapRooms(rooms,props,clues);
            roomWrappers.forEach(RoomWrapper::describeRoom);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private List<RoomWrapper> wrapRooms(List<Room> rooms, List<Prop> props, List<Clue> clues){
        List<RoomWrapper> roomWrappers = new ArrayList<>();
        rooms.forEach(room -> {
            RoomWrapper roomWrapper = new RoomWrapper();
            roomWrapper.room = room;
            roomWrapper.props = props.stream().filter(prop -> prop.getRoomId()==room.getId()).toList();
            roomWrapper.clues = clues.stream().filter(clue -> clue.getRoomId()==room.getId()).toList();
            roomWrappers.add(roomWrapper);
        });
        return roomWrappers;
    }
    private void printInventoryHeader(List<Room> rooms, List<Prop> props){
        StringBuilder header = new StringBuilder();
        header.append("There are ").append(rooms.size()).append(" rooms in your escape room.\n")
                .append("Taking into account all your assets, the value of your escape room is : ").append(props.stream().map(Prop::getValue).reduce(0, Integer::sum))
                .append("\n********************* ROOM LIST ********************\n");
        System.out.print(header);
    }

}
