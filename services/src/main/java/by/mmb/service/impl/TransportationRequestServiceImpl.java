package by.mmb.service.impl;

import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.enams.RequestStatus;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Cargo;
import by.mmb.model.transportationRequest.Request;
import by.mmb.repo.CargoRepository;
import by.mmb.repo.CityRepository;
import by.mmb.repo.RequestRepository;
import by.mmb.service.TransportationRequestService;
import by.mmb.service.validation.ValidationObject;
import by.mmb.sevice.SecurityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author andrew.maksimovich
 */
@Service
public class TransportationRequestServiceImpl implements TransportationRequestService {

    private final CityRepository cityRepository;
    private final CargoRepository cargoRepository;
    private final RequestRepository requestRepository;
    private final ValidationObject<Cargo> cargoValidationObject;
    private final SecurityService securityService;

    @Autowired
    public TransportationRequestServiceImpl(CityRepository cityRepository,
                                            CargoRepository cargoRepository,
                                            RequestRepository requestRepository,
                                            ValidationObject<Cargo> cargoValidationObject, SecurityService securityService) {
        this.cityRepository = cityRepository;
        this.cargoRepository = cargoRepository;
        this.requestRepository = requestRepository;
        this.cargoValidationObject = cargoValidationObject;
        this.securityService = securityService;
    }

    @Transactional
    @Override
    public boolean createNewRequest(@NonNull TransportationRequestDto dto) throws AppsException {
        if(dto.getCityTo() != dto.getCityFrom() &&
                cityRepository.isPresent(List.of(dto.getCityFrom(), dto.getCityTo())) &&
                cargoValidationObject.isValid(dto.getCargo())
        ){
            long idCargo = cargoRepository.createNewCargo(dto.getCargo());
            Request request = Request.builder()
                    .cityFrom(dto.getCityFrom())
                    .cityTo(dto.getCityTo())
                    .dateCreate(LocalDateTime.now())
                    .dateRefresh(LocalDateTime.now())
                    .countKM(dto.getCountKM())
                    .status(RequestStatus.OPEN)
                    .cargoId(idCargo)
                    .userId(1) // TODO !!!!!!!!!!!!
                    .additionalParams(dto.getAdditionalParams())
                    .build();
            long idNewRequest = requestRepository.createRequest(request);
            return idNewRequest > 0;
        }
        return false;
    }

    @Override
    public LocalDateTime refreshUpRequest(long id) {
        return null;
    }

    @Override
    public boolean updateRequest(TransportationRequestDto dto) {
        return false;
    }

    @Override
    public boolean changeStatusOfRequest(RequestStatus status) {
        return false;
    }

    @Override
    public boolean deleteRequest(long id) {
        return false;
    }

    @Override
    public List<Request> getAllRequest() {
        return null;
    }
}
