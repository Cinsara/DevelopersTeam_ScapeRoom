package escapeRoom.Service.NotificationService;

import escapeRoom.Notification.Notification;
import escapeRoom.Service.CrudeService;
import escapeRoom.Service.GetAllService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class NotificationService implements CrudeService<Notification>, GetAllService<Notification> {
    @Override
    public String getTableName() {
        return "notification";
    }

    @Override
    public Notification mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("notification_id");
        String content = resultSet.getString("notification_content");
        LocalDate dataSent = resultSet.getObject("notification_dataSent", LocalDate.class);
        return new Notification(id,content,dataSent);
    }

    @Override
    public Notification create(Notification entity) throws SQLException {
        return null;
    }

    @Override
    public Optional<Notification> read(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Notification update(Notification entity) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
