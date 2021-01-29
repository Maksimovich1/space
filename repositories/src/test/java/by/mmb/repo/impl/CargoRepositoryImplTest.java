package by.mmb.repo.impl;

import by.mmb.common.AbstractCommonTestRepository;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Cargo;
import by.mmb.repo.CargoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CargoRepositoryImplTest extends AbstractCommonTestRepository {

    @Test
    void createNewCargo() throws AppsException {
        CargoRepository cargoRepository = new CargoRepositoryImpl(jdbcTemplate);
        Cargo cargo = new Cargo();
        cargo.setName("Химия");
        cargo.setHeight(10);
        cargo.setLength(20);
        cargo.setWeight(30);
        cargo.setWidth(40);
        long idCargo1 = cargoRepository.createNewCargo(cargo);
        Assert.assertEquals(idCargo1, 1);

        cargo = new Cargo();
        cargo.setName("Металл");
        cargo.setHeight(10);
        cargo.setLength(20);
        cargo.setWeight(30);
        cargo.setWidth(40);
        long idCargo = cargoRepository.createNewCargo(cargo);
        Assert.assertEquals(idCargo, 2);
    }
}