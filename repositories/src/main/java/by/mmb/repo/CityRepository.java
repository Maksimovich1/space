package by.mmb.repo;

import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.City;

import java.util.List;

/**
 * @author andrew.maksimovich
 */
public interface CityRepository {

    boolean addNewCity(City city);
    City getCityById(long idCity);
    boolean isPresent(List<Integer> ids) throws AppsException;
}
