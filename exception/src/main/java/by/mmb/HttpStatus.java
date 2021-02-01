package by.mmb;

import lombok.Getter;

/**
 * Обрабатываемые http статусы в приложении
 *
 * @author andrew.maksimovich
 */
public enum HttpStatus {
    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400);
    @Getter
    private int code;

    HttpStatus(int code) {
        this.code = code;
    }
}