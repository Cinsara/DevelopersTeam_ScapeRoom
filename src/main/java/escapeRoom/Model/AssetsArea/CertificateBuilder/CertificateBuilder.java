package escapeRoom.Model.AssetsArea.CertificateBuilder;

import escapeRoom.Model.AssetsArea.AssetBuilder.AssetBuilder;

public class CertificateBuilder implements AssetBuilder<Certificate> {
    private int user_id;
    private int game_id;

    @Override
    public AssetBuilder<Certificate> setUserId(int user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public AssetBuilder<Certificate> setGameId(int game_id) {
        this.game_id = game_id;
        return this;
    }

    @Override
    public Certificate build() {
        return new Certificate(user_id,game_id);
    }
}
