package by.mmb.controllers.transportation;

import by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing;
import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.exception.AppsException;
import by.mmb.model.transportationRequest.Request;
import by.mmb.service.TransportationRequestService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @PostMapping("/request")
    public boolean createTransReq(@RequestBody @NonNull TransportationRequestDto transportationRequestDto) throws AppsException {
        log.trace("Создание нового запроса: " + transportationRequestDto);
        return transportationRequestService.createNewRequest(transportationRequestDto);
    }

    @ExceptionHandlerProcessing
    @PutMapping("/request")
    public boolean updateTransReq(@RequestBody @NonNull TransportationRequestDto transportationRequestDto) {
        return transportationRequestService.updateRequest(transportationRequestDto);
    }

    @ExceptionHandlerProcessing
    @PutMapping("/refresh/{id}")
    public LocalDateTime refresh(@PathVariable long id) throws AppsException {
        log.trace("Обновление: " + id + " refresh!");
        return transportationRequestService.refreshUpRequest(id);
    }

    @ExceptionHandlerProcessing
    @GetMapping("/all")
    public List<Request> requests() {
        return transportationRequestService.getAllRequest();
    }

}
