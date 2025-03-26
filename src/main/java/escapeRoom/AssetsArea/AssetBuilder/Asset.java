package escapeRoom.AssetsArea.AssetBuilder;

import escapeRoom.PeopleArea.User;


import java.time.LocalDate;

public interface Asset {
    void setId(int id);
    int getId();
    int getUser_id();
    int getGame_id();
    public <T> void expressAsset(User user, T entity, LocalDate date);
}
