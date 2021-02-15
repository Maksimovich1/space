package by.mmb.service;

import by.mmb.exception.AppsException;
import by.mmb.model.analytic.AnalyticType;

/**
 * @author andrew.maksimovich
 */
public interface DictionaryService {

    int STATUS_CREATE = 1;
    int STATUS_UPDATE = 2;

    AnalyticType getByIdDictionary(final int idType, final boolean withValue) throws AppsException;

    int saveOrUpdateDictionary(AnalyticType analyticType);

    void deleteDictionary(final int idType, final boolean isCascade) throws AppsException;
}
