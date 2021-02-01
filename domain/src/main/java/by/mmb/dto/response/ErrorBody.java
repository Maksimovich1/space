package by.mmb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Тело ответа если произошла ошибка
 *
 * @author andrew.maksimovich
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBody {
    private String message;
    private String messageWithDetails;
    private String messageForClient;
    private int code;
    private LocalDateTime time;
}