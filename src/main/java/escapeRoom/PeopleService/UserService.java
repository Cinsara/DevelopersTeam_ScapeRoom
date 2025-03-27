package escapeRoom.PeopleService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.PeopleArea.User;
import escapeRoom.Service.GetAllService;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class UserService implements GetAllService<User> {
    private final Connection connection = ConnectionManager.getConnection();

    public Connection getConnection() {
        return connection;
    }

    public UserService() throws SQLException {
    }

    @Override
    public String getTableName() {
        return "customer";
    }

    @Override
    public User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("customer_id");
        String name = resultSet.getString("customer_name");
        String lastname = resultSet.getString("customer_lastname");
        String email = resultSet.getString("customer_mail");
        LocalDate dob = resultSet.getObject("customer_dob", LocalDate.class);
        String phoneNumber = resultSet.getString("customer_phone_number");
        boolean notificationStatus = resultSet.getBoolean("customer_notifications");
        LocalDate dateRegistration = resultSet.getObject("customer_dateRegistration", LocalDate.class);

        User user = new User(name,lastname,email,phoneNumber,dob,notificationStatus);
        user.setId(id);
        user.setDateRegistration(dateRegistration);
        return user;
    }

    @Override
    public User create(User user) throws SQLException {
        String query = "INSERT INTO " + getTableName() + "(customer_name, customer_lastname, customer_dob, customer_mail," +
                "customer_phone_number, customer_notifications, customer_dateRegistration) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDate(3, Date.valueOf(user.getDob()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setBoolean(6, user.isNotificationStatus());
            preparedStatement.setDate(7, Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();

            System.out.println(getGeneratedId(preparedStatement));
            user.setId(getGeneratedId(preparedStatement));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public Optional<User> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public User update(User user) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET customer_name = ?, customer_lastname = ?, customer_dob = ?, customer_mail = ?, " +
                " customer_phone_number = ?, customer_notifications = ?, customer_dateRegistration = ? WHERE customer_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDate(3, Date.valueOf(user.getDob()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setBoolean(6, user.isNotificationStatus());
            preparedStatement.setDate(7, Date.valueOf(user.getDateRegistration()));
            preparedStatement.setInt(8,user.getId());
            preparedStatement.executeUpdate();
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }
}


