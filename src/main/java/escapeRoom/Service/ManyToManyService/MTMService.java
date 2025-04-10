package escapeRoom.Service.ManyToManyService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MTMService {

    protected final Connection connection;

    protected MTMService(Connection connection) throws SQLException{
        this.connection = connection;
    }

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

    public List<Integer> getMatchesReverse(int target_id) throws SQLException {
        String query = "SELECT " + getOriginColumn() + " FROM " +getTableName()+ " WHERE "+ getTargetColumn()+" = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, target_id);
            List<Integer> matches = new ArrayList<>();
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    matches.add(resultSet.getInt(getOriginColumn()));
                }
            }
            return matches;
        }
    }

    public boolean createMatch(int origin_id, int target_id) throws SQLException {
        String query = "INSERT into " + getTableName()+ " ("+ getTargetColumn()+","+ getOriginColumn()+") VALUE (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,target_id);
            preparedStatement.setInt(2,origin_id);
            return preparedStatement.executeUpdate() == 1;
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
