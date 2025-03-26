package escapeRoom.AssetsArea.AssetBuilder;

public interface AssetBuilder<T extends Asset> {
    AssetBuilder<T> setUserId(int user_id);
    AssetBuilder<T> setGameId(int game_id);
    T build();
}
