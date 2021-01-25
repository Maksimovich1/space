package by.mmb.service.validation.impl;

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
    public boolean isValid(@NonNull Cargo cargo) {
        return cargo.getHeight() > 0
                && cargo.getLength() > 0
                && cargo.getWeight() > 0
                && StringUtils.hasText(cargo.getName());
    }
}
