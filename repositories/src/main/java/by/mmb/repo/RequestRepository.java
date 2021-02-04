package by.mmb.repo;

import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.AdditionalParam;
import by.mmb.model.transportationRequest.Request;
import by.mmb.util.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий по работе с заявками
 *
 * @author andrew.maksimovich
 */
public interface RequestRepository {

    /**
     * Создание новой заявки, сохраняет вместе с доп параметрами
     *
     * @param request заполненная заявка
     * @return ид сохраненной заявки
     * @throws AppsException может бросить если переданы не вылидные параметры
     */
    long createRequest(Request request) throws AppsException;

    /**
     * Обновить по времени (поднять заявку)
     *
     * @param idRequest ид заяки
     * @param userId    ид пользователя
     * @return время обновления
     * @throws AppsException может бросить если переданы не вылидные параметры
     */
    LocalDateTime refreshUpRequest(long idRequest, long userId) throws AppsException;

    /**
     * Изменение статуса заявки
     *
     * @param idRequest ид заявки
     * @param status    статус в который переводим заявку
     * @return true если успешно
     * @throws AppsException может бросить если переданы не вылидные параметры
     */
    int changeStatus(long idRequest, RequestStatus status) throws AppsException;

    /**
     * Получение заявки без дополнительных параметров
     * Можно задать параметр что если заявка была удалена ее все равно нужно отдать
     *
     * @param id      ид заявки
     * @param removed true - если заявка удалена то всеравно отдать, false будет пусто optional
     * @return Optional на случай если заявки может не существовоть
     */
    Optional<Request> getRequestById(long id, boolean removed);

    /**
     * Получение заявки с доп параметрами
     *
     * @param idRequest ид заявки
     * @return optional будет пустым если передан не существующий ид
     * в остальных случаях буден точно возвращен не нулевой объект
     */
    Optional<Request> getRequestByIdWithAdditionalParam(long idRequest);

    /**
     * Получение только параметров по ид заявки
     *
     * @param idRequest ид заявки
     * @return optional будет пустым если передан не существующий ид
     * в остальных случаях буден точно возвращен не нулевой объект
     */
    Optional<AdditionalParam> getAdditionalParamOfRequestById(long idRequest);

    /**
     * Добавляет или обновляет дод параметры
     *
     * @param listParams лист пар
     * @return true если успешно
     */
    boolean setParamOrUpdate(List<Pair<String, String>> listParams);

    /**
     * Удаляет по id заявку
     * Физического удаления не происходит, меняем статус на удалено
     *
     * @param idRequest ид заявки
     * @return Возвращает количество удаленных строк
     */
    int deleteRequestById(long idRequest);
}
