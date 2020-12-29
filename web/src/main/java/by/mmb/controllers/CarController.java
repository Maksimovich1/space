package by.mmb.controllers;

import by.mmb.dto.CarDTO;
import by.mmb.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/car")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/getAll")
    public List<CarDTO> getAllCars() {
        log.trace("Получение всех машин");
        return Collections.emptyList();
    }

    @GetMapping("{id}")
    public CarDTO getCar(@PathVariable("id") long id) {
        log.trace("Получение машины по ид = " + id);
        return carService.getCarById(id);
    }

    @PostMapping("/")
    public long saveCar(@RequestBody CarDTO car){
        log.trace("Сохранение новой машины");
        return carService.saveNewCar(car);
    }

    @GetMapping("/getCarsByRoute/{id}")
    public List<CarDTO> getCarsByRouteIds(@PathVariable long id){
        log.trace("Получение всех машин на маршруте =" + id);
        return carService.getCardsByRouteId(id);
    }

}
