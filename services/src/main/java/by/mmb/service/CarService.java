package by.mmb.service;

import by.mmb.dto.CarDTO;

import java.util.List;

public interface CarService {

    CarDTO getCarById(long id);

    long saveNewCar(CarDTO carDTO);

    List<CarDTO> getCardsByRouteId(long id);

    List<CarDTO> getAllCarUserById(long idUser);

    boolean update(CarDTO carDTO);
}