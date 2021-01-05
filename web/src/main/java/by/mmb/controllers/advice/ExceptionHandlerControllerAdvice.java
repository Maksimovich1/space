package by.mmb.controllers.advice;

import by.mmb.dto.response.ErrorBody;
import by.mmb.exception.AppsException;
import by.mmb.exception.AutoAppsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Обработчик ошибок для конечного клиента
 *
 * @author andrew.maksimovich
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @Value("${default.error: Ошибка на сервере}")
    private String errorDefault;

    @ExceptionHandler(value = {AppsException.class})
    public ResponseEntity<ErrorBody> handleInvalidInputException(AppsException ex) {
        ErrorBody err = ErrorBody.builder()
                .message(ex.getErrorCode().getMessage())
                .messageForClient(ex.getErrorCode().getMessageForClient())
                .code(ex.getErrorCode().getExternalCode())
                .time(LocalDateTime.now())
                .build();
        log.error(StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage(), ex);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AutoAppsException.class})
    public ResponseEntity<ErrorBody> handleInvalidInputException(AutoAppsException ex) {
        Throwable cause = ex.getRootCause();
        ErrorBody err = ErrorBody.builder()
                .message(cause.getMessage())
                .messageForClient(errorDefault)
                .code(ex.getErrorCode().getExternalCode())
                .time(LocalDateTime.now())
                .build();
        log.error(cause.getMessage(), cause);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}