package by.mmb.repo.rowMapperCar;

import by.mmb.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet resultSet, int i) throws SQLException {
        return Car.builder()
                .id(resultSet.getLong(1))
                .numberCar(resultSet.getString(2))
                .countPlace(resultSet.getInt(3))
                .status(resultSet.getInt(4))
                .driverId(resultSet.getInt(5))
                .build();
    }
}
