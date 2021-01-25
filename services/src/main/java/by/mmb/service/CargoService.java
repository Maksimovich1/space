package by.mmb.service;

import by.mmb.model.transportationRequest.Cargo;

/**
 * @author andrew.maksimovich
 */
public interface CargoService {
    boolean checkValid(Cargo cargo);
}
