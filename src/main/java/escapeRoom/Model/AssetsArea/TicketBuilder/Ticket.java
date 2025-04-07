package escapeRoom.Model.AssetsArea.TicketBuilder;

import escapeRoom.Model.AssetsArea.AssetBuilder.Asset;
import escapeRoom.Model.PeopleArea.User;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import java.time.LocalDate;

public class Ticket implements Asset {
    private final int captain_id;
    private final int game_id;
    private float price;

    public LocalDate getSaleDate() {
        return saleDate;
    }

    private LocalDate saleDate;
    private int id;

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
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getId() {
        return id;
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
            System.out.println("Ticket purchased by " + user.getName() +" "+user.getLastname()+ ", for the game " +
                    game.getId() + ", with the date " + game.getDate());
        } else {
            throw new IllegalArgumentException("Entity must be of type Game.");
        }
    }
}
