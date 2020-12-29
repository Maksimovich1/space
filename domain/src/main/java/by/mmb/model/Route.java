package by.mmb.model;

import lombok.Data;

@Data
public class Route {
    private long id;
    /**
     * ref City
     */
    private int startCity;
    /**
     * ref City
     */
    private int finishCity;
    private double price;
}
