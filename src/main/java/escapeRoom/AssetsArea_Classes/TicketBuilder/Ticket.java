package escapeRoom.AssetsArea_Classes.TicketBuilder;

import escapeRoom.AssetsArea_Classes.AssetBuilder.Asset;
import escapeRoom.AssetsArea_Classes.Classes_testing.Game;
import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;
import java.time.LocalDate;

public class Ticket implements Asset {
    private final int captain_id;
    private final int game_id;
    private float price;
    private LocalDate saleDate;

    public Ticket(int captain_id, int game_id, float price, LocalDate saleDate){
        this.captain_id = captain_id;
        this.game_id = game_id;
        this.price = price;
        this.saleDate = LocalDate.now();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int getUser_id() {
        return this.captain_id;
    }

    @Override
    public int getGame_id() {
        return this.game_id;
    }

    @Override
    public <T> void expressAsset(User user, T entity, LocalDate date) {
        if (entity instanceof Game game) {
            System.out.println("Ticket purchased by " + user + ", for the game " +
                    game + ", with the date of " + date);
        } else {
            throw new IllegalArgumentException("Entity must be of type Room.");
        }
    }
}
