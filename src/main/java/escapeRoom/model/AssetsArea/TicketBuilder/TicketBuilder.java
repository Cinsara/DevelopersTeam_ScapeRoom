package escapeRoom.model.AssetsArea.TicketBuilder;

import escapeRoom.model.AssetsArea.AssetBuilder.AssetBuilder;

import java.time.LocalDate;

public class TicketBuilder implements AssetBuilder<Ticket> {
    private int captain_id;
    private int game_id;
    private float price;
    private LocalDate saleDate;

    public TicketBuilder setPrice(float price){
        this.price = price;
        return this;
    }

    @Override
    public AssetBuilder<Ticket> setUserId(int user_id) {
        this.captain_id = user_id;
        return this;
    }

    @Override
    public AssetBuilder<Ticket> setGameId(int game_id) {
        this.game_id = game_id;
        return this;
    }

    @Override
    public Ticket build() {
        return new Ticket(captain_id,game_id,price,saleDate);
    }
}
