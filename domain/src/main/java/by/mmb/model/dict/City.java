package by.mmb.model.dict;

import lombok.Data;

@Data
public class City {
    private long id;
    private String name;
    /**
     * широта
     */
    private long latitude;
    /**
     * долгота
     */
    private long longitude;
}
