package escapeRoom.Service.AssetService;

import escapeRoom.model.AssetsArea.AssetBuilder.Asset;
import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.CrudeService;
import escapeRoom.Service.GetAllService;

import java.sql.*;
import java.util.Optional;

abstract class AssetService <T extends Asset> implements GetAllService<T> {
    protected final Connection connection = ConnectionManager.getConnection();

    protected AssetService() throws SQLException {
    }


     @Override
     public T create(T entity) throws SQLException {
         String query = "INSERT into " + getTableName() + "(customer_customer_id,game_game_id) VALUES (?,?)";
         try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
             preparedStatement.setInt(1,entity.getUser_id());
             preparedStatement.setInt(2,entity.getGame_id());
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
     public Optional<T> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?";
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
    public T update(T entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET customer_customer_id = ?, game_game_id = ? WHERE "+getTableName()+"_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,entity.getUser_id());
            preparedStatement.setInt(2, entity.getGame_id());
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
