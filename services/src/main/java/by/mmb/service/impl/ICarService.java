package by.mmb.service.impl;

import by.mmb.dto.CarDTO;
import by.mmb.model.Car;
import by.mmb.repo.CarRepository;
import by.mmb.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICarService implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public ICarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDTO getCarById(long id) {
        Car carById = carRepository.getCarById(id);
        CarDTO carDTO = new CarDTO();
        return carDTO;
    }

    @Override
    public long saveNewCar(CarDTO carDTO) {
        return 0;
    }

    @Override
    public List<CarDTO> getCardsByRouteId(long id) {
        return null;
    }

    @Override
    public List<CarDTO> getAllCarUserById(long idUser) {
        return null;
    }

    @Override
    public boolean update(CarDTO carDTO) {
        return false;
    }
}
