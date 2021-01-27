package by.mmb.enams;

import lombok.Getter;

/**
 * Описывает статусы заявки на перевозку
 *
 * @author andrew.maksimovich
 */
@Getter
public enum RequestStatus {
    OPEN(1),
    CLOSE(2),
    DELETE(3);

    private final int code;

    RequestStatus(int code) {
        this.code = code;
    }

    public static RequestStatus getStatusByCode(final int code) {
        switch (code) {
            case 1:
                return OPEN;
            case 2:
                return CLOSE;
            case 3:
                return DELETE;
            default:
                throw new IllegalArgumentException("Не валидный код = " + code);
        }
    }
}