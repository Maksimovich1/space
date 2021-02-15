package by.mmb.repo;

import by.mmb.model.analytic.AnalyticType;
import by.mmb.model.analytic.AnalyticValue;

import java.util.List;
import java.util.Optional;

/**
 * @author andrew.maksimovich
 */
public interface DictionaryRepository {
    Optional<AnalyticType> getAnalTypeByIdType(int idTypeCode);

    List<AnalyticValue> getAnalValuesByIdType(int idTypeCode);

    boolean createAnalValue(AnalyticValue analyticValue);

    boolean createAnalValue(List<AnalyticValue> analyticValue);

    boolean createAnalType(AnalyticType analyticType);

    boolean updateAnalValue(AnalyticValue analyticValue);

    boolean updateAnalType(AnalyticType analyticType);

    boolean deleteAnalValue(List<Integer> idValues, int idTypeCode);

    boolean deleteAnalType(int idTypeCode);
}
