package by.mmb.model.transportationRequest;

import lombok.Data;

/**
 * @author andrew.maksimovich
 */
@Data
public class City {
    private long id;
    private String name;
    /**
     * analytic type = 800
     */
    private long district;
    /**
     * широта
     */
    private long latitude;
    /**
     * долгота
     */
    private long longitude;
}
