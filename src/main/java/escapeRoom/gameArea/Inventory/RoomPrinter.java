package escapeRoom.gameArea.Inventory;


import escapeRoom.gameArea.RoomBuilder.Room;
import escapeRoom.gameArea.RoomCreation.RoomCreationService;

public class RoomPrinter {

    private final RoomCreationService roomCreationService;

    public RoomPrinter(RoomCreationService roomService) {
        this.roomCreationService = roomService;
    }

    public void printAllRooms() {
        System.out.println("📜 List of All Rooms:");
        for (Room room : roomCreationService.getAllRooms()) {
            System.out.println(room);
        }
    }

    public void printLastRoomCreated() {
        System.out.println("📜 Here's your new room:");
        System.out.println(roomCreationService.getLastRoom());

    }
}
