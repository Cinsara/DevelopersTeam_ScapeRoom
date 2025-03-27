package escapeRoom.Service.AssetService;



import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TicketService extends AssetService<Ticket> {
    protected TicketService() throws SQLException {
    }

    @Override
    public String getTableName() {
        return "ticket";
    }

    @Override
    public Ticket mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(getTableName()+"_id");
        int userId = resultSet.getInt("captain_customer_id");
        int gameId = resultSet.getInt("game_game_id");
        float price = resultSet.getFloat("ticket_price");
        LocalDate saleDate = resultSet.getObject("ticket_saleDate",LocalDate.class);
        Ticket newTicket = new Ticket(userId,gameId,price,saleDate);
        newTicket.setId(id);
        return newTicket;
    }

    @Override
    public Ticket create(Ticket entity) throws SQLException {
        String query = "INSERT into " + getTableName() + "(captain_customer_id,game_game_id,ticket_price,ticket_saleDate) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,entity.getUser_id());
            preparedStatement.setInt(2,entity.getGame_id());
            preparedStatement.setFloat(3,entity.getPrice());
            preparedStatement.setObject(4,entity.getSaleDate());
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
    public Ticket update(Ticket entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET captain_customer_id = ?, game_game_id = ?,ticket_price = ?,ticket_saleDate = ? WHERE "+getTableName()+"_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,entity.getUser_id());
            preparedStatement.setInt(2, entity.getGame_id());
            preparedStatement.setFloat(3,entity.getPrice());
            preparedStatement.setObject(4,entity.getSaleDate());;
            preparedStatement.setInt(5,entity.getId());
            preparedStatement.executeUpdate();
        }
        return entity;
    }
}
