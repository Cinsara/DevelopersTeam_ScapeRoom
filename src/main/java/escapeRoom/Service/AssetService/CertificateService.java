package escapeRoom.Service.AssetService;

import escapeRoom.model.AssetsArea.CertificateBuilder.Certificate;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateService extends AssetService<Certificate> {
    public CertificateService(Connection connection) throws SQLException {
        super(connection);
    }
    @Override
    public Certificate mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(getTableName()+"_id");
        int userId = resultSet.getInt("customer_customer_id");
        int gameId = resultSet.getInt("game_game_id");
        Certificate newCertificate = new Certificate(userId,gameId);
        newCertificate.setId(id);;
        return newCertificate;
    }

    @Override
    public String getTableName() {
        return "certificate";
    }
}
