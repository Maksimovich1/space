package by.mmb.repo.impl;

import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Cargo;
import by.mmb.repo.CargoRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author andrew.maksimovich
 */
@Repository
public class CargoRepositoryImpl implements CargoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CargoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long createNewCargo(@NonNull Cargo cargo) throws AppsException {
        var sql = "insert into test.cargo (name_cargo, weight_kg, length_sm, width_sm, height_sm) values (?,?,?,?,?)";
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cargo.getName());
            ps.setInt(2, cargo.getWeight());
            ps.setInt(3, cargo.getLength());
            ps.setInt(4, cargo.getWidth());
            ps.setInt(5, cargo.getHeight());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() == null) {
            //TODO !!!!!!!!!!!!!!!
            throw new AppsException("", -12315);
        }
        return keyHolder.getKey().longValue();
    }
}
