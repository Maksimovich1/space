package by.mmb.repo.rowMapperCar.dictionary;

import by.mmb.model.analytic.AnalyticType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author andrew.maksimovich
 */
public class DictionaryRowMapper implements RowMapper<AnalyticType> {
    @Override
    public AnalyticType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AnalyticType.builder()
                .id(rs.getInt(1))
                .codeType(rs.getInt(2))
                .description(rs.getString(3))
                .analyticValues(new ArrayList<>())
                .build();
    }
}
