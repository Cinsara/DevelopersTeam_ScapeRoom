package escapeRoom.Service.NotificationService;

import escapeRoom.Model.Notification.Notification;
import escapeRoom.Service.GetAllService;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class NotificationService implements GetAllService<Notification> {

    private final Connection connection;

    public NotificationService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getTableName() {
        return "notification";
    }

    @Override
    public Notification mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("notification_id");
        String content = resultSet.getString("notification_content");
        LocalDate dataSent = resultSet.getObject("notification_dateSent", LocalDate.class);
        return new Notification(id,content,dataSent);
    }

    @Override
    public Notification create(Notification entity) throws SQLException {
        String query = "INSERT into " + getTableName() + "(notification_content,notification_dateSent) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,entity.getContent());
            preparedStatement.setObject(2,entity.getDateSent());
            preparedStatement.executeUpdate();
            try {
                entity.setId(getGeneratedId(preparedStatement));
            }catch(SQLException e){
                System.err.println("Warning: Unable to retrieve generated ID. " + e.getMessage());
            }
            return entity;
        }
    }

    @Override
    public Optional<Notification> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + getTableName()+"_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return Optional.of(mapResultSetToEntity(resultSet));
                }else{
                    return Optional.empty();
                }
            }
        }

    }

    @Override
    public Notification update(Notification entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET notification_content = ?, notification_dateSent = ? WHERE notification_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,entity.getContent());
            preparedStatement.setObject(2, entity.getDateSent());
            preparedStatement.setInt(3,entity.getId());
            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE "+ getTableName()+"_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate()==1;
        }
    }
}
