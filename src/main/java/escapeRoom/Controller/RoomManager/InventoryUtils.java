package escapeRoom.Controller.RoomManager;

import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.Prop;
import escapeRoom.Model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.Model.GameArea.RoomBuilder.Room;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    private InventoryUtils(){};

    static public List<RoomWrapper> wrapRooms(List<Room> rooms, List<Prop> props, List<Clue> clues){
        List<RoomWrapper> roomWrappers = new ArrayList<>();
        rooms.forEach(room -> {
            RoomWrapper roomWrapper = new RoomWrapper(room);
            roomWrapper.props = props.stream().filter(prop -> prop.getRoomId()==room.getId()).toList();
            roomWrapper.clues = clues.stream().filter(clue -> clue.getRoomId()==room.getId()).toList();
            roomWrappers.add(roomWrapper);
        });
        return roomWrappers;
    }


    static public class RoomWrapper{
        private int id;
        private String name;
        private String theme;
        private Difficulty difficulty;
        List<Prop> props;
        List<Clue> clues;

        public RoomWrapper(Room room) {
            this.id = room.getId();
            this.name = room.getName();
            this.theme = room.getTheme();
            this.difficulty = room.getDifficulty();
        }

        public void describeRoom(){
            StringBuilder description = new StringBuilder();
            description.append("----------------------------\n")
                    .append("Room ").append(name).append(" ID: ").append(id).append("\n")
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
}
