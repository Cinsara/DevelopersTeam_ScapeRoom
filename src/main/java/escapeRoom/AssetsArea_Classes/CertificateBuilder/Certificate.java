package escapeRoom.AssetsArea_Classes.CertificateBuilder;

import escapeRoom.AssetsArea_Classes.AssetBuilder.Asset;
import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;
import escapeRoom.AssetsArea_Classes.Classes_testing.Room_Classes.Room;

import java.time.LocalDate;

public class Certificate implements Asset {
    private final int user_id;
    private final int game_id;

    public Certificate(int user_id, int game_id){
        this.user_id = user_id;
        this.game_id = game_id;
    }

    @Override
    public int getUser_id() {
        return this.user_id;
    }

    @Override
    public int getGame_id() {
        return this.game_id;
    }

    @Override
    public <T> void expressAsset(User user, T entity, LocalDate date) {
        if (entity instanceof Room room) {
            System.out.println("Certificate issued to " + user.getName() +
                    ", for the " + room.getName() + ", with the date of " + date);
        } else {
            throw new IllegalArgumentException("Entity must be of type Room.");
        }
    }

}
