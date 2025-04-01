package escapeRoom.Manager;

import java.time.LocalDate;

public class GameCoordinates {

        private LocalDate date;
        private int roomId;

        public GameCoordinates(LocalDate date, int roomId) {
            this.date = date;
            this.roomId = roomId;
        }

        public LocalDate getDate() {
            return date;
        }
        public void setDate(LocalDate date) {
            this.date = date;
        }
        public int getRoomId() {
            return roomId;
        }
        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }
    
}
