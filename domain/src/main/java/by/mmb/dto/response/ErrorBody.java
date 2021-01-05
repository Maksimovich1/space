package by.mmb.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Тело ответа если произошла ошибка
 *
 * @author andrew.maksimovich
 */
@Data
@Builder
public class ErrorBody {
    private String message;
    private String messageForClient;
    private int code;
    private LocalDateTime time;
}