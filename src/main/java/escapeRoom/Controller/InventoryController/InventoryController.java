package escapeRoom.Controller.InventoryController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.Clue;
import escapeRoom.model.GameArea.CluePropFactory.Prop;
import escapeRoom.model.GameArea.RoomBuilder.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventoryController {

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
            description.append(props.size()).append( "props:\n");
            props.forEach(prop -> {
                description.append("- ").append(prop.getType()).append(" value ").append(prop.getValue()).append("\n");
            });
            description.append("---Total Room Value: ").append(props.stream().map(Prop::getValue).reduce(0, Integer::sum)).append("\n");
            System.out.print(description);
        }
    }

    public void showInventory(){
        try{
            List<Room> rooms = new RoomService().getAllEntities(ConnectionManager.getConnection());
            List<Prop> props = new PropService(ConnectionManager.getConnection()).getAllEntities(ConnectionManager.getConnection());
            List<Clue> clues = new ClueService(ConnectionManager.getConnection()).getAllEntities(ConnectionManager.getConnection());
            StringBuilder header = new StringBuilder();
            header.append("There are ").append(rooms.size()).append(" rooms in your escape room.\n")
                    .append("Taking into account all your assets, the value of your escape room is : ").append(props.stream().map(Prop::getValue).reduce(0, Integer::sum))
                    .append("\n********************* ROOM LIST ********************\n");
            System.out.print(header);
            List<RoomWrapper> roomWrappers = new ArrayList<>();
            rooms.forEach(room -> {
                RoomWrapper roomWrapper = new RoomWrapper();
                roomWrapper.room = room;
                roomWrapper.props = props.stream().filter(prop -> prop.getRoomId()==room.getId()).toList();
                roomWrapper.clues = clues.stream().filter(clue -> clue.getRoomId()==room.getId()).toList();
                roomWrappers.add(roomWrapper);
            });
            roomWrappers.forEach(RoomWrapper::describeRoom);



        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
