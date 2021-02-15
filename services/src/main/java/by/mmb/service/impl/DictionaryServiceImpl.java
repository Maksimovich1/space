package by.mmb.service.impl;

import by.mmb.HttpStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.analytic.AnalyticType;
import by.mmb.model.analytic.AnalyticValue;
import by.mmb.repo.DictionaryRepository;
import by.mmb.service.DictionaryService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис по работе со справочниками
 *
 * @author andrew.maksimovich
 */
@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    /**
     * Получение справочника по коду
     * Если значений аналитики нет, вернем просто тип.
     *
     * @param idType ид справочново типа
     * @return аналитический тип наполненый значениями
     * @throws AppsException если типа не существует то вернется BAD_REQUEST
     */
    @Override
    public AnalyticType getByIdDictionary(int idType, boolean withValue) throws AppsException {
        Optional<AnalyticType> analTypeByIdType = dictionaryRepository.getAnalTypeByIdType(idType);
        AnalyticType analyticType = analTypeByIdType
                .orElseThrow(() -> new AppsException(() -> "Не найдена аналитика по коду " + idType, -10235, HttpStatus.BAD_REQUEST));

        List<AnalyticValue> analValuesByIdType = dictionaryRepository.getAnalValuesByIdType(idType);
        analyticType.setAnalyticValues(analValuesByIdType);
        return analyticType;
    }

    @Transactional
    @Override
    public int saveOrUpdateDictionary(@NonNull AnalyticType analyticType) {
        Optional<AnalyticType> analTypeByIdType = dictionaryRepository.getAnalTypeByIdType(analyticType.getCodeType());
        if (analTypeByIdType.isEmpty()) {
            dictionaryRepository.createAnalType(analyticType);

            if (analyticType.getAnalyticValues() != null && !analyticType.getAnalyticValues().isEmpty()) {
                dictionaryRepository.createAnalValue(analyticType.getAnalyticValues());
            }
            return STATUS_CREATE;
        } else {
            AnalyticType analyticTypeInDb = analTypeByIdType.get();
            ifNeedUpdate(analyticTypeInDb, analyticType);
            return STATUS_UPDATE;
        }
    }

    @Override
    public void deleteDictionary(int idType, boolean isCascade) throws AppsException {
        AnalyticType dictionary = getByIdDictionary(idType, true);
        if (isCascade || (dictionary.getAnalyticValues() == null) || dictionary.getAnalyticValues().isEmpty()) {
            dictionaryRepository.deleteAnalType(idType);
        } else {
            throw new AppsException(() -> "Указано не каскадное удаление, хотя значения в справочнике есть значения!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновление аналитического типа и значений
     * Метод должен работать если тип уже есть в бд
     *
     * @param analyticTypeInDb тип который находится в БД
     * @param analyticType     тип который пришел для обновления
     */
    private void ifNeedUpdate(@NonNull AnalyticType analyticTypeInDb, @NonNull AnalyticType analyticType) {
        if (!analyticTypeInDb.getDescription().equals(analyticType.getDescription())) {
            dictionaryRepository.updateAnalType(analyticType);
        }
        if (analyticType.getAnalyticValues() == null || analyticType.getAnalyticValues().isEmpty()) {
            return;
        }
        List<AnalyticValue> analValuesByIdTypeDB = dictionaryRepository.getAnalValuesByIdType(analyticTypeInDb.getCodeType());
        if (analValuesByIdTypeDB != null && !analValuesByIdTypeDB.isEmpty()) {
            List<Integer> collectForRemove =
                    analyticType.getAnalyticValues()
                            .stream()
                            .map(analyticValue -> analyticType.getCodeType())
                            .collect(Collectors.toList());
            dictionaryRepository.deleteAnalValue(collectForRemove, analyticType.getCodeType());
        }
        dictionaryRepository.createAnalValue(analyticType.getAnalyticValues());
    }
}
