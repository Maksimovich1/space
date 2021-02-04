package by.mmb.service.impl;

import by.mmb.HttpStatus;
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

    @Transactional(rollbackFor = {RuntimeException.class, AppsException.class})
    @Override
    public long createNewRequest(@NonNull TransportationRequestDto dto) throws AppsException {
        if (dto.getCityTo() != dto.getCityFrom() &&
                cityRepository.isPresent(List.of(dto.getCityFrom(), dto.getCityTo())) &&
                cargoValidationObject.isValid(dto.getCargo())
        ) {
            long idCargo = cargoRepository.createNewCargo(dto.getCargo());
            Request request = Request.builder()
                    .cityFrom(dto.getCityFrom())
                    .cityTo(dto.getCityTo())
                    .dateCreate(LocalDateTime.now())
                    .dateRefresh(LocalDateTime.now())
                    .countKM(dto.getCountKM())
                    .status(RequestStatus.OPEN)
                    .cargoId(idCargo)
                    .userId(securityService.getCurrentUser().getIdUser())
                    .additionalParam(dto.getAdditionalParams())
                    .build();
            return requestRepository.createRequest(request);
        }
        throw new AppsException(() -> "Города не должны быть одинаковые!", -10235, HttpStatus.BAD_REQUEST);
    }

    @Override
    public LocalDateTime refreshUpRequest(long id) throws AppsException {
        return requestRepository.refreshUpRequest(id, securityService.getCurrentUser().getIdUser());
    }

    @Override
    public void updateRequest(TransportationRequestDto dto) {
    }

    @Override
    public boolean changeStatusOfRequest(RequestStatus status) {
        return false;
    }

    @Transactional(rollbackFor = {RuntimeException.class, AppsException.class})
    @Override
    public boolean deleteRequest(long idRequest) throws AppsException {
        long idCurrentUser = securityService.getCurrentUser().getIdUser();
        Request currentRequest = requestRepository.getRequestById(idRequest, false)
                .orElseThrow(() -> new AppsException(() -> "Не найдена заявка c ид = " + idRequest, -11212, HttpStatus.BAD_REQUEST));

        if (currentRequest.getUserId() != idCurrentUser) {
            throw new AppsException(() -> "Переданная заяка не принадлежит данному пользователю!", -11213, HttpStatus.BAD_REQUEST);
        }
        int countRowDelete = requestRepository.changeStatus(idRequest, RequestStatus.DELETE);
        if (countRowDelete != 1) {
            throw new AppsException(() -> "Не удалось удалить заявку!");
        }
        return true;
    }

    @Override
    public List<Request> getAllRequest() {
        return null;
    }

    @Override
    public TransportationRequestDto getRequestById(long idRequest) throws AppsException {
        Request request = requestRepository.getRequestById(idRequest, false)
                .orElseThrow(() -> new AppsException(() -> "Не найдена заявка c ид = " + idRequest, -11212, HttpStatus.BAD_REQUEST));
        TransportationRequestDto requestDto = new TransportationRequestDto();
        requestDto.setCargo(cargoRepository.getCargoById(request.getCargoId(), true));
        requestDto.setCityFrom(request.getCityFrom());
        requestDto.setCityTo(request.getCityTo());
        requestDto.setCountKM(request.getCountKM());
        requestDto.setAdditionalParams(request.getAdditionalParam());
        return requestDto;
    }
}
