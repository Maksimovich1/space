package by.mmb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Car {

    private long id;
    private long brandId;
    private long modelId;
    private String numberCar;
    private int countPlace;
    private int driverId;
    /**
     * status car work(1) or free(2) or not available(3)
     */
    private int status;
}
