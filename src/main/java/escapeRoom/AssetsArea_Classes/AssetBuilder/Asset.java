package escapeRoom.AssetsArea_Classes.AssetBuilder;

import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;

import java.time.LocalDate;

public interface Asset {
    int getUser_id();
    int getGame_id();
    public <T> void expressAsset(User user, T entity, LocalDate date);
}
