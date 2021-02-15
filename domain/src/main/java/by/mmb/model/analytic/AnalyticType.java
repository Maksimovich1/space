package by.mmb.model.analytic;

import lombok.*;

import java.util.List;

/**
 * @author andrew.maksimovich
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AnalyticType {
    /**
     * внутренниц ид справочика
     */
    private int id;
    /**
     * код (ид) справочника
     */
    private int codeType;
    /**
     * Описание справочника
     */
    private String description;
    /**
     * Значения справочника
     */
    @Setter private List<AnalyticValue> analyticValues;
}