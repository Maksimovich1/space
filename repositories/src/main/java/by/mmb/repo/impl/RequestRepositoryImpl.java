package by.mmb.repo.impl;

import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.exception.ExceptionUtility;
import by.mmb.model.transportationRequest.Request;
import by.mmb.repo.RequestRepository;
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
        val sql = "insert into test.request (user_id, cargo_id, city_from, city_to, count_km, date_reg, date_refresh, analytic_value_id_status) VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, request.getUserId());
            ps.setLong(2, request.getCargoId());
            ps.setLong(3, request.getCityFrom());
            ps.setLong(4, request.getCityTo());
            ps.setLong(5, request.getCountKM());
            ps.setDate(6, new java.sql.Date(new Date().getTime()));
            ps.setDate(7, new java.sql.Date(new Date().getTime()));
            ps.setInt(8, request.getStatus().getCode());

            return ps;
        }, keyHolder);

        ExceptionUtility.checkException(keyHolder.getKey(), NullPointerException.class, () -> "");

        return keyHolder.getKey().longValue();
    }

    @Override
    public LocalDateTime refreshUpRequest(long idRequest) {
        return null;
    }

    @Override
    public boolean changeStatus(long idRequest, RequestStatus status) {
        return false;
    }
}
