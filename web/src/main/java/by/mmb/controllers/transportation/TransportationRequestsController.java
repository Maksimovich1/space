package by.mmb.controllers.transportation;

import by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing;
import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.dto.response.SpaceResponseModel;
import by.mmb.exception.AppsException;
import by.mmb.service.TransportationRequestService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author andrew.maksimovich
 */
@Slf4j
@RestController
@RequestMapping("api/requests")
public class TransportationRequestsController {


    private final TransportationRequestService transportationRequestService;

    @Autowired
    public TransportationRequestsController(TransportationRequestService transportationRequestService) {
        this.transportationRequestService = transportationRequestService;
    }


    @ExceptionHandlerProcessing
    @GetMapping("/request/{id}")
    public ResponseEntity<SpaceResponseModel> getRequestById(@PathVariable("id") long idRequest) throws AppsException {
        log.trace("Получение заявки по ид = " + idRequest);
        TransportationRequestDto transportationRequestDto =
                transportationRequestService.getRequestById(idRequest);
        return new ResponseEntity<>(SpaceResponseModel.successOf(transportationRequestDto), HttpStatus.OK);

    }

    @ExceptionHandlerProcessing
    @PostMapping("/request")
    public ResponseEntity<SpaceResponseModel> createTransReq(@RequestBody @NonNull TransportationRequestDto transportationRequestDto) throws AppsException {
        log.trace("Создание нового запроса: " + transportationRequestDto);
        long idNewRequest = transportationRequestService.createNewRequest(transportationRequestDto);
        return new ResponseEntity<>(SpaceResponseModel.successOf(idNewRequest), HttpStatus.CREATED);
    }

    @ExceptionHandlerProcessing
    @PutMapping("/request")
    public ResponseEntity<SpaceResponseModel> updateTransReq(@RequestBody @NonNull TransportationRequestDto transportationRequestDto) {
        log.trace("Обновление заявки: " + transportationRequestDto);
        transportationRequestService.updateRequest(transportationRequestDto);
        return new ResponseEntity<>(SpaceResponseModel.successOfEmptyBody(), HttpStatus.OK);
    }

    @ExceptionHandlerProcessing
    @PutMapping("/refresh/{id}")
    public ResponseEntity<SpaceResponseModel> refresh(@PathVariable long id) throws AppsException {
        log.trace("Обновление: " + id + " refresh!");
        LocalDateTime localDateTime = transportationRequestService.refreshUpRequest(id);
        return new ResponseEntity<>(SpaceResponseModel.successOf(localDateTime), HttpStatus.OK);
    }

    @ExceptionHandlerProcessing
    @GetMapping("/all")
    public ResponseEntity<Object /*TODO PAGEABLE object*/> requests() {
        return new ResponseEntity<>(transportationRequestService.getAllRequest(), HttpStatus.OK);
    }

    @ExceptionHandlerProcessing
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SpaceResponseModel> deleteById(@PathVariable("id") long id) throws AppsException {
        boolean statusOfDelete = transportationRequestService.deleteRequest(id);
        return new ResponseEntity<>(SpaceResponseModel.successOf(statusOfDelete), HttpStatus.OK);
    }

}
