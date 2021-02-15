package by.mmb.controllers.dictionary;

import by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing;
import by.mmb.dto.request.dictionary.RequestForDeleteDictionary;
import by.mmb.dto.response.SpaceResponseModel;
import by.mmb.exception.AppsException;
import by.mmb.model.analytic.AnalyticType;
import by.mmb.service.DictionaryService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Эндпоинт для работы со справочниками
 *
 * @author andrew.maksimovich
 */
@Slf4j
@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * Возвращает справочник по типу
     *
     * @param idType    тип справочника (ид)
     * @param withValue возращать со значениями в справочнике (1), другое значение без
     * @return SpaceResponseModel внутри лист со справочником
     * @throws AppsException any mistakes
     */
    @ExceptionHandlerProcessing
    @GetMapping("/dictionary/{id}/{withValue}")
    public ResponseEntity<SpaceResponseModel> getAnalTypeById(@PathVariable("id") final int idType, @PathVariable("withValue") final int withValue) throws AppsException {
        log.debug("getDictionaryById is work...");
        AnalyticType analyticType = dictionaryService.getByIdDictionary(idType, withValue == 1);
        return new ResponseEntity<>(SpaceResponseModel.successOf(analyticType), HttpStatus.OK);
    }

    /**
     * Сохранение или обновление справочника и его записей.
     * Если записи нет то она будет добавлена.
     * Если коды совпадают а текст нет то будет текст обновлен.
     *
     * @param analyticType Объект справочника
     * @return SpaceResponseModel с телом Empty, если был создан новый справочник будет код 201
     * если обновление то 200
     */
    @ExceptionHandlerProcessing
    @PutMapping("/dictionary")
    public ResponseEntity<SpaceResponseModel> saveOrUpdateAnalType(@RequestBody @NonNull AnalyticType analyticType) {
        log.debug("saveOrUpdateDictionary is work...");
        int status = dictionaryService.saveOrUpdateDictionary(analyticType);
        return new ResponseEntity<>(SpaceResponseModel.successOfEmptyBody(), status == 1 ? HttpStatus.CREATED : HttpStatus.OK);
    }

    /**
     * Удаление справочника
     *
     * @param request Объект содержащий информацию для удаления
     * @return SpaceResponseModel с телом Empty
     * @throws AppsException any mistakes
     */
    @ExceptionHandlerProcessing
    @DeleteMapping("/dictionary")
    public ResponseEntity<SpaceResponseModel> deleteAnalType(@RequestBody @NonNull RequestForDeleteDictionary request) throws AppsException {
        log.debug("deleteAnalType is work...");
        dictionaryService.deleteDictionary(request.idDictionary, request.isCascade);
        return new ResponseEntity<>(SpaceResponseModel.successOfEmptyBody(), HttpStatus.OK);
    }

}
