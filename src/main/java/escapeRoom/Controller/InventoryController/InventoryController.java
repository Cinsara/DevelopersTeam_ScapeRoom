package escapeRoom.Controller.InventoryController;


import escapeRoom.Controller.RoomManager.InventoryUtils;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.Prop;
import escapeRoom.Model.GameArea.RoomBuilder.Room;
import escapeRoom.Controller.RoomManager.InventoryUtils.RoomWrapper;

import java.sql.SQLException;
import java.util.List;

public class InventoryController {
    private RoomManager roomManager;

    public InventoryController(RoomManager roomManager){
        this.roomManager = roomManager;
    }


    public void showInventory(){
            List<List<?>> printableEntities = roomManager.prepPrintableRooms();
            List<Room> rooms = (List<Room>) printableEntities.getFirst();
            List<Prop> props = (List<Prop>) printableEntities.get(1);
            List<RoomWrapper> roomWrappers = (List<InventoryUtils.RoomWrapper>) printableEntities.get(2);
            printInventoryHeader(rooms,props);
            roomWrappers.forEach(RoomWrapper::describeRoom);
    }

    private void printInventoryHeader(List<Room> rooms, List<Prop> props){
        StringBuilder header = new StringBuilder();
        header.append("There are ").append(rooms.size()).append(" rooms in your escape room.\n")
                .append("Taking into account all your assets, the value of your escape room is : ").append(props.stream().map(Prop::getValue).reduce(0, Integer::sum))
                .append("\n********************* ROOM LIST ********************\n");
        System.out.print(header);
    }

}
