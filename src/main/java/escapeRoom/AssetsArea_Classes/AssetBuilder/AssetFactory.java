package escapeRoom.AssetsArea_Classes.AssetBuilder;

import escapeRoom.AssetsArea_Classes.CertificateBuilder.CertificateBuilder;
import escapeRoom.AssetsArea_Classes.RewardBuilder.RewardBuilder;
import escapeRoom.AssetsArea_Classes.TicketBuilder.TicketBuilder;

public class AssetFactory {

    public Asset createAsset(String type, int user_id, int game_id){
        return createAsset(type,user_id,game_id,0);
    }

    public Asset createAsset(String type, int user_id, int game_id, float price){
        AssetBuilder<?> builder = switch (type.toLowerCase()) {
            case "certificate" -> new CertificateBuilder();
            case "reward" -> new RewardBuilder();
            case "ticket" -> new TicketBuilder().setPrice(price);
            default -> throw new IllegalStateException("Unexpected value: " + type.toLowerCase());
        };
        return builder
                .setUserId(user_id)
                .setGameId(game_id)
                .build();
    }
}
