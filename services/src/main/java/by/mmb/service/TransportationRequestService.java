package by.mmb.service;

import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author andrew.maksimovich
 */
public interface TransportationRequestService {

    long createNewRequest(TransportationRequestDto dto) throws AppsException;

    LocalDateTime refreshUpRequest(long id) throws AppsException;

    void updateRequest(TransportationRequestDto dto);

    boolean changeStatusOfRequest(RequestStatus status);

    boolean deleteRequest(long id) throws AppsException;

    List<Request> getAllRequest();

    TransportationRequestDto getRequestById(long idRequest) throws AppsException;
}
