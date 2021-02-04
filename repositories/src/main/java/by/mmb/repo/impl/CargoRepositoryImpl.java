package by.mmb.repo.impl;

import by.mmb.exception.AppsException;
import by.mmb.exception.ExceptionUtility;
import by.mmb.model.transportationRequest.Cargo;
import by.mmb.repo.CargoRepository;
import by.mmb.repo.rowMapperCar.cargo.CargoRowMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.function.Supplier;

/**
 * @author andrew.maksimovich
 */
@Slf4j
@Repository
public class CargoRepositoryImpl implements CargoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CargoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long createNewCargo(@NonNull Cargo cargo) throws AppsException {
        var sql = "insert into space.cargo (name_cargo, weight_kg, length_sm, width_sm, height_sm) values (?,?,?,?,?)";
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

        ExceptionUtility.checkException(keyHolder.getKey(), NullPointerException.class, () -> "Ключ оказался пустым");
        return keyHolder.getKey().longValue();
    }

    @Override
    public Cargo getCargoById(long cargoId, boolean showOnlyInOpenRequest) {
        Supplier<String> sql = showOnlyInOpenRequest ?
                () -> "select c.id, c.name_cargo, c.weight_kg, c.length_sm, c.width_sm, c.height_sm from space.cargo c " +
                        "join request r on c.id = r.cargo_id " +
                        "where c.id = ? and r.analytic_value_id_status = 1"
                :
                () -> "select id, name_cargo, weight_kg, length_sm, width_sm, height_sm from space.cargo " +
                        "where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql.get(), new CargoRowMapper(), cargoId);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not found cargo by id " + cargoId);
            return null;
        }
    }
}
