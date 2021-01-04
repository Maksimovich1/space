package by.mmb.repo.impl;

import by.mmb.model.Car;
import by.mmb.repo.CarRepository;
import by.mmb.repo.rowMapperCar.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ICarRepositoryJdbc implements CarRepository {

    private final JdbcTemplate jdbcTemplate;

    public ICarRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Car getCarById(long id) {
//        Integer integer = jdbcTemplate.queryForObject("select count(1) from cars", Integer.class);
        return jdbcTemplate.queryForObject("select id, number_car, count_place, status, driver_id from cars where id = ?", new CarRowMapper(), id);
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }
}
