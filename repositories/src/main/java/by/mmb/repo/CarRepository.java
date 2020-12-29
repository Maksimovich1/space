package by.mmb.repo;

import by.mmb.model.Car;

import java.util.List;

public interface CarRepository {
    Car getCarById(long id);
    List<Car> getAllCars();
}
