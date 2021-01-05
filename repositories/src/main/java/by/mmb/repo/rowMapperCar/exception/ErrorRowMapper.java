package by.mmb.repo.rowMapperCar.exception;

import by.mmb.code.ErrorCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author andrew.maksimovich
 */
public class ErrorRowMapper implements RowMapper<ErrorCode> {
    @Override
    public ErrorCode mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ErrorCode(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getString(4)
        );
    }
}