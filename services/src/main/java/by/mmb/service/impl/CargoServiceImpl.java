package by.mmb.service.impl;

import by.mmb.model.transportationRequest.Cargo;
import by.mmb.service.CargoService;
import org.springframework.stereotype.Service;

/**
 * @author andrew.maksimovich
 */
@Service
public class CargoServiceImpl implements CargoService {
    @Override
    public boolean checkValid(Cargo cargo) {
        return false;
    }
}