package by.mmb.repo;

import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Request;

import java.time.LocalDateTime;

/**
 * @author andrew.maksimovich
 */
public interface RequestRepository {

    long createRequest(Request request) throws AppsException;

    LocalDateTime refreshUpRequest(long idRequest);

    boolean changeStatus(long idRequest, RequestStatus status);
}
