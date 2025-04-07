package escapeRoom.Service;

import java.sql.SQLException;
import java.util.Optional;

public interface CheckExistenceService<T> extends CrudeService<T>  {

    default boolean existEntity(int id, Class<T> type) throws SQLException, AbsentEntityException {
        Optional<T> potentialEntity = read(id);
        if (potentialEntity.isPresent()){
            return true;
        } else {
            throw new AbsentEntityException(id, type);
        }
    }
}
