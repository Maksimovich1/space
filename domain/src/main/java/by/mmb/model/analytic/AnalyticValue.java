package by.mmb.model.analytic;

import by.mmb.util.Pair;
import lombok.*;

import java.util.List;

/**
 * Запись в справочнике
 *
 * @author andrew.maksimovich
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(exclude = "relationPair")
public class AnalyticValue {

    /**
     * Внутреннее ид записи справочника
     */
    private int id;
    /**
     * ид справочника
     */
    private int codeTypeId;
    /**
     * ид записи внешнее
     */
    private int codeValueId;
    /**
     * Описание записи
     */
    private String description;

    /**
     * Доп связи
     */
    private List<Pair<Integer, Integer>> relationPair;

}
