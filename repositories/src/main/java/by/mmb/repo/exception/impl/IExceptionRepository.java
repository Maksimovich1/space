package by.mmb.repo.exception.impl;

import by.mmb.code.ErrorCode;
import by.mmb.repo.exception.ExceptionRepository;
import by.mmb.repo.rowMapperCar.exception.ErrorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий по работе с ошибками
 *
 * @author andrew.maksimovich
 */
@Repository
public class IExceptionRepository implements ExceptionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IExceptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ErrorCode getErrorCodeByCode(int internalCode) {
        String sql = "select ec.external_code, ic.internal_code, ec.message_ru, ic.message from test.internal_codes ic\n" +
                "inner join test.external_codes ec on ic.external_id = ec.external_code\n" +
                "where ic.internal_code = ?;";
        return jdbcTemplate.queryForObject(sql, new ErrorRowMapper(), internalCode);
    }
}
