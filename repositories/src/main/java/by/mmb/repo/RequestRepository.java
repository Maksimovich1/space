package by.mmb.repo;

import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Request;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author andrew.maksimovich
 */
public interface RequestRepository {

    long createRequest(Request request) throws AppsException;

    LocalDateTime refreshUpRequest(long idRequest, long userId) throws AppsException;

    boolean changeStatus(long idRequest, RequestStatus status) throws AppsException;

    /**
     * Получение заявки без дополнительных параметров
     *
     * @param id ид заявки
     * @return Optional на случай если заявки может не существовоть
     */
    Optional<Request> getRequestById(long id);
}
