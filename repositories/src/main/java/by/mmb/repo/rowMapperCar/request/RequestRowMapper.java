package by.mmb.repo.rowMapperCar.request;

import by.mmb.CommonUtility;
import by.mmb.enams.RequestStatus;
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

//        if (!resultSet.isBeforeFirst()) {
//            //TODO создать unchecked ex!!!
//            throw new AppsException(() -> "Записи не найдены!", -11212, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return Request.builder()
                .id(resultSet.getLong(1))
                .userId(resultSet.getLong(2))
                .cargoId(resultSet.getLong(3))
                .cityFrom(resultSet.getLong(4))
                .cityTo(resultSet.getLong(5))
                .countKM(resultSet.getInt(6))
                .status(RequestStatus.getStatusByCode(resultSet.getInt(7)))
                .dateCreate(CommonUtility.convert(resultSet.getDate(8)))
                .dateRefresh(CommonUtility.convert(resultSet.getDate(9)))
                .build();
    }
}