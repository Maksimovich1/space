package by.mmb.repo.impl;

import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.exception.ExceptionUtility;
import by.mmb.model.transportationRequest.Request;
import by.mmb.repo.RequestRepository;
import by.mmb.repo.rowMapperCar.request.RequestRowMapper;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author andrew.maksimovich
 */
@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long createRequest(@NonNull Request request) throws AppsException {
        val sql = "insert into space.request (user_id, cargo_id, city_from, city_to, count_km, date_reg, date_refresh, analytic_value_id_status) VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, request.getUserId());
            ps.setLong(2, request.getCargoId());
            ps.setLong(3, request.getCityFrom());
            ps.setLong(4, request.getCityTo());
            ps.setLong(5, request.getCountKM());
            ps.setObject(6, new Date());
            ps.setObject(7, new Date());
            ps.setInt(8, request.getStatus().getCode());

            return ps;
        }, keyHolder);

        ExceptionUtility.checkException(keyHolder.getKey(), NullPointerException.class, () -> "");

        return keyHolder.getKey().longValue();
    }

    @Override
    public LocalDateTime refreshUpRequest(long idRequest, long userId) throws AppsException {
        int countRowsUpdate = jdbcTemplate.update(
                "update space.request set date_refresh = ? where id = ? and user_id = ?",
                new Date(),
                idRequest,
                userId);
        if (countRowsUpdate == 1) {
            return LocalDateTime.now();
        }
        throw new AppsException(() -> "Не найдена заявка для данного пользователя", -10235);
    }

    @Override
    public boolean changeStatus(long idRequest, RequestStatus status) {
        return false;
    }

    @Override
    public Optional<Request> getRequestById(long id) {
        Request request = jdbcTemplate.queryForObject(
                "select id, user_id, cargo_id, city_from, city_to, count_km, analytic_value_id_status, date_reg, date_refresh  from  space.request where id = ?;",
                new RequestRowMapper(),
                id);
        return Optional.ofNullable(request);
    }
}