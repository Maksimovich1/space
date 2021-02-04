package by.mmb.controllers.advice;

import by.mmb.dto.response.ErrorBody;
import by.mmb.dto.response.SpaceResponseModel;
import by.mmb.dto.response.enums.Empty;
import by.mmb.exception.AppsException;
import by.mmb.exception.AutoAppsException;
import lombok.NonNull;
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
    @Value("${default.countSymbolsMessageWithError:40}")
    private int countSymbolsMessageWithError;

    @ExceptionHandler(value = {AppsException.class})
    public ResponseEntity<SpaceResponseModel<Empty>> handleInvalidInputException(AppsException ex) {
        String messageAboutError = ex.getRootCause() != null ? trimMessageToNeedSizeIfNeeded(ex.getRootCause().getMessage()) : "";
        ErrorBody err = ErrorBody.builder()
                .message(ex.getErrorCode().getMessage())
                .messageWithDetails(ex.getMessage() + (StringUtils.hasText(messageAboutError) ? ("\nINFO: " + messageAboutError) : ""))
                .messageForClient(ex.getErrorCode().getMessageForClient())
                .code(ex.getErrorCode().getExternalCode())
                .time(LocalDateTime.now())
                .build();
        log.error(StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage(), ex);
        HttpStatus status = resolverHttpStatus(ex.getHttpStatus());
        return new ResponseEntity<>(SpaceResponseModel.failOf(err), status);
    }

    @ExceptionHandler(value = {AutoAppsException.class})
    public ResponseEntity<SpaceResponseModel<Empty>> handleInvalidInputException(AutoAppsException ex) {
        Throwable cause = ex.getRootCause();
        ErrorBody err = ErrorBody.builder()
                .message(cause.getMessage())
                .messageWithDetails(ex.getMessage() + "\nINFO: " + cause.getStackTrace()[0])
                .messageForClient(errorDefault)
                .code(ex.getErrorCode().getExternalCode())
                .time(LocalDateTime.now())
                .build();
        log.error(cause.getMessage(), cause);
        return new ResponseEntity<>(SpaceResponseModel.failOf(err), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрезаем ошибку если нужно
     *
     * @param message сообщение об ошибке
     * @return валидная ошибка по размеру
     */
    private String trimMessageToNeedSizeIfNeeded(@NonNull String message) {
        if (message.length() > countSymbolsMessageWithError) {
            return message.substring(0, countSymbolsMessageWithError);
        }
        return message;
    }

    /**
     * Подставляем нужный статус изходя из ошибки
     *
     * @param httpStatus код переданный из ошибки
     * @return если код не найден вернется INTERNAL_SERVER_ERROR
     * иначе будет возвращен нужный код
     */
    private HttpStatus resolverHttpStatus(int httpStatus) {
        HttpStatus resolve = HttpStatus.resolve(httpStatus);
        return resolve == null ? HttpStatus.INTERNAL_SERVER_ERROR : resolve;
    }
}