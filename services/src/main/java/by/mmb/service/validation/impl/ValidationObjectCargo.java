package by.mmb.service.validation.impl;

import by.mmb.HttpStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Cargo;
import by.mmb.service.validation.ValidationObject;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author andrew.maksimovich
 */
@Component
public class ValidationObjectCargo implements ValidationObject<Cargo> {
    @Override
    public boolean isValid(@NonNull Cargo cargo) throws AppsException {
        boolean ifValid = cargo.getHeight() > 0
                && cargo.getLength() > 0
                && cargo.getWeight() > 0
                && StringUtils.hasText(cargo.getName());
        if (!ifValid) {
            throw new AppsException(() -> "Не валидные параметры груза!", -10235, HttpStatus.BAD_REQUEST);
        }
        return true;
    }
}
