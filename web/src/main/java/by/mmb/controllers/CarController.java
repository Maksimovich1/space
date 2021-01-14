package by.mmb.controllers;

import by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing;
import by.mmb.dto.CarDTO;
import by.mmb.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@Slf4j
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ExceptionHandlerProcessing
    @GetMapping("/getAllByUser/{id}")
    public List<CarDTO> getAllCars(@PathVariable("id") long idUser) {
        log.trace("Получение всех машин пользователя");
        return carService.getAllCarUserById(idUser);
    }

    @ExceptionHandlerProcessing
    @GetMapping("{id}")
    public CarDTO getCar(@PathVariable("id") long id) {
        log.trace("Получение машины по ид = " + id);
        return carService.getCarById(id);
    }

    @ExceptionHandlerProcessing
    @PostMapping("/")
    public long saveCar(@RequestBody CarDTO car) {
        log.trace("Сохранение новой машины: " + car);
        return carService.saveNewCar(car);
    }

    @ExceptionHandlerProcessing
    @PutMapping("/")
    public boolean updateCar(@RequestBody CarDTO carDTO) {
        log.trace("Обновление машины" + carDTO);
        return carService.update(carDTO);
    }

    @ExceptionHandlerProcessing
    @GetMapping("/getCarsByRoute/{id}")
    public List<CarDTO> getCarsByRouteIds(@PathVariable long id) {
        log.trace("Получение всех машин на маршруте =" + id);
        return carService.getCardsByRouteId(id);
    }

}
