package by.mmb.repo.impl;

import by.mmb.HttpStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.City;
import by.mmb.repo.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author andrew.maksimovich
 */
@Slf4j
@Repository
public class CityRepositoryImpl implements CityRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addNewCity(City city) {
        return false;
    }

    @Override
    public City getCityById(long idCity) {
        return null;
    }

    @Override
    public boolean isPresent(List<Integer> ids) throws AppsException {
        log.trace("Проверяем существуют ли переданные ид городов " + ids);
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Integer countRow = jdbcTemplate.queryForObject(String.format("select count(*) from space.city where id in (%s)", inSql), Integer.class, ids.toArray());
        if (countRow == null || countRow != ids.size()) {
            log.error("Переданные города не существуют или недоступны!");
            throw new AppsException(() -> "Переданные города не существуют или недоступны!", -10235, HttpStatus.BAD_REQUEST);
        }
        log.trace("Проверка прошла успешно!");
        return true;
    }
}