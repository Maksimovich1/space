package by.mmb.repo.rowMapperCar.cargo;

import by.mmb.model.transportationRequest.Cargo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author andrew.maksimovich
 */
public class CargoRowMapper implements RowMapper<Cargo> {
    @Override
    public Cargo mapRow(ResultSet resultSet, int i) throws SQLException {
        return Cargo.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .weight(resultSet.getInt(3))
                .length(resultSet.getInt(4))
                .width(resultSet.getInt(5))
                .height(resultSet.getInt(6))
                .build();
    }
}
