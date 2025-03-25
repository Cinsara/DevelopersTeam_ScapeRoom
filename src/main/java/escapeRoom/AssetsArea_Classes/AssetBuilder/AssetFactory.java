package escapeRoom.AssetsArea_Classes.AssetBuilder;

import escapeRoom.AssetsArea_Classes.CertificateBuilder.CertificateBuilder;
import escapeRoom.AssetsArea_Classes.RewardBuilder.RewardBuilder;
import escapeRoom.AssetsArea_Classes.TicketBuilder.TicketBuilder;

public class AssetFactory {

    public Asset createAsset(AssetType type, int user_id, int game_id){
        return createAsset(type,user_id,game_id,0);
    }

    public Asset createAsset(AssetType type, int user_id, int game_id, float price){
        AssetBuilder<?> builder = switch (type) {
            case CERTIFICATE -> new CertificateBuilder();
            case REWARD -> new RewardBuilder();
            case TICKET -> new TicketBuilder().setPrice(price);
        };
        return builder
                .setUserId(user_id)
                .setGameId(game_id)
                .build();
    }
}
