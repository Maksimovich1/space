package by.mmb.repo.rowMapperCar.request;

import by.mmb.CommonUtility;
import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Request;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author andrew.maksimovich
 */
public class RequestRowMapper implements RowMapper<Request> {
    @SneakyThrows
    @Override
    public Request mapRow(ResultSet resultSet, int i) throws SQLException {

        if (!resultSet.isBeforeFirst()) {
            //TODO создать unchecked ex!!!
            throw new AppsException(() -> "Записи не найдены!", -11212);
        }

        return Request.builder()
                .userId(resultSet.getLong(1))
                .cargoId(resultSet.getLong(2))
                .cityFrom(resultSet.getLong(3))
                .cityTo(resultSet.getLong(4))
                .countKM(resultSet.getInt(5))
                .status(RequestStatus.getStatusByCode(resultSet.getInt(6)))
                .dateCreate(CommonUtility.convert(resultSet.getDate(7)))
                .dateRefresh(CommonUtility.convert(resultSet.getDate(8)))
                .build();
    }
}