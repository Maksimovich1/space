package by.mmb.repo;

import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Cargo;

/**
 * @author andrew.maksimovich
 */
public interface CargoRepository {

    long createNewCargo(Cargo cargo) throws AppsException;
}
