package by.mmb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Car {

    private long id;
    /**
     * analytic type = 2
     */
    private long brandId;
    /**
     * analytic type = 3
     */
    private long modelId;

    private String numberCar;
    private int countPlace;
    private int driverId;
    /**
     * analytic type = 1
     */
    private int status;

    private LocalDateTime dateReg;
}
