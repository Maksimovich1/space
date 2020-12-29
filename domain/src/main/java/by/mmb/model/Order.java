package by.mmb.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private long id;
    private long routeId;
    private long userId;
    private LocalDateTime timeOrder;
    /**
     * status order : 1-посадка выполнена 2-подтвержден 3-выполнен 4-отменен
     */
    private int status;
}
