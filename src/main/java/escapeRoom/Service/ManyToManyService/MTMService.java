package escapeRoom.Service.ManyToManyService;

import escapeRoom.ConnectionManager.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MTMService {

    protected Connection connection = ConnectionManager.getConnection();

    protected MTMService() throws SQLException{
    }

    private int getGeneratedId(PreparedStatement preparedStatement) throws SQLException {
        try( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
            if (generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else {
                throw new SQLException("No key was generated");
            }
        }
    };

    public List<Integer> getMatches(int origin_id) throws SQLException {
        String query = "SELECT " + getTargetColumn() + " FROM " +getTableName()+ " WHERE "+ getOriginColumn() +" = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, origin_id);
            List<Integer> matches = new ArrayList<>();
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                 matches.add(resultSet.getInt(getTargetColumn()));
                }
            }
            return matches;
        }
    }
    public int[] createMatch(int origin_id, int target_id) throws SQLException {
        String query = "INSERT into " + getTableName()+ " ("+ getTargetColumn()+","+ getOriginColumn()+") VALUE (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,target_id);
            preparedStatement.setInt(2,origin_id);
            preparedStatement.executeUpdate();
            return new int[]{origin_id, target_id};
        }
    }
    public boolean deleteMatch(int origin_id,int target_id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE "+ getTargetColumn()+ "= ? AND " + getOriginColumn() +" =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,target_id);
            preparedStatement.setInt(2,origin_id);
            return preparedStatement.executeUpdate() == 1;
        }
    }

    abstract String getTableName();
    abstract String getOriginColumn();
    abstract String getTargetColumn();


}
