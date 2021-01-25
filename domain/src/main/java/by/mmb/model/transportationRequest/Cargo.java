package by.mmb.model.transportationRequest;

import by.mmb.model.AdditionalParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Описывает сущность груза для перевозки
 *
 * @author andrew.maksimovich
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    private long id;
    /**
     * Наименование груза
     */
    private String name;
    /**
     * вес
     */
    private int weight;
    /**
     * длина
     */
    private int length;
    /**
     * ширина
     */
    private int width;
    /**
     * высота
     */
    private int height;
    /**
     * Дополнительные параметры
     */
    private List<AdditionalParam> additionalParams;
}