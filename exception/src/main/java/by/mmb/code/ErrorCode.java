package by.mmb.code;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс представляющий код ошибки.
 * <p>
 * -9999 считается неизвестной ошибкой как для
 * externalCode так и для internalCode
 *
 * @author andrew.maksimovich
 */
@Getter
@Setter
@EqualsAndHashCode
public class ErrorCode {
    /**
     * Код который будет вываливаться наружу
     */
    private int externalCode;

    /**
     * Сообщение для клиента
     */
    private String messageForClient;

    /**
     * Сообщение общее
     */
    private String message;
    /**
     * Код который быдет только внутри
     */
    private final int internalCode;

    public ErrorCode(int internalCode) {
        this.internalCode = internalCode;
        this.externalCode = -9999;
    }

    public ErrorCode(int externalCode, int internalCode) {
        this.externalCode = externalCode;
        this.internalCode = internalCode;
    }

    public ErrorCode(int externalCode, int internalCode, String messageForClient, String message) {
        this.externalCode = externalCode;
        this.messageForClient = messageForClient;
        this.message = message;
        this.internalCode = internalCode;
    }
}
