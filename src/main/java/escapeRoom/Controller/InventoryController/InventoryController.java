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
        List<Clue> clue;
        Room room;

    }

    public void showInventory(){
        try{
            List<Room> rooms = new RoomService().getAllEntities(ConnectionManager.getConnection());
            List<Prop> props = new PropService(ConnectionManager.getConnection()).getAllEntities(ConnectionManager.getConnection());
            List<Clue> clues = new ClueService(ConnectionManager.getConnection()).getAllEntities(ConnectionManager.getConnection());
            List<RoomWrapper> roomWrappers = new ArrayList<>();
            rooms.forEach(room -> {

                room.setProps_id(props.stream().filter(prop -> prop.getRoomId()==room.getId()).map(Prop::getId).collect(Collectors.toList()));
                room.setClues_id(clues.stream().filter(clue -> clue.getRoomId()==room.getId()).map(Clue::getId).collect(Collectors.toList()));
            });




        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
