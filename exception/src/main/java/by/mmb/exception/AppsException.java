package by.mmb.exception;

import by.mmb.HttpStatus;
import by.mmb.code.ErrorCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Supplier;

/**
 * The AppsException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages.
 *
 * @author andrew.maksimovich
 */
public class AppsException extends Exception {

    private static final long serialVersionUID = 7718828512143293558L;

    @Getter
    private Class aClass;

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Throwable rootCause;

    @Getter
    private final int httpStatus;

    public AppsException(@NonNull Supplier<String> message) {
        super(message.get());
        this.errorCode = new ErrorCode();
        this.rootCause = null;
        this.httpStatus = 500;
    }

    public AppsException(@NonNull Supplier<String> message, int internalCode, @NonNull HttpStatus httpStatus) {
        super(message.get());
        errorCode = new ErrorCode(internalCode);
        this.rootCause = null;
        this.httpStatus = httpStatus.getCode();
    }

    public AppsException(@NonNull Supplier<String> message, @NonNull HttpStatus httpStatus) {
        super(message.get());
        this.errorCode = new ErrorCode();
        this.rootCause = null;
        this.httpStatus = httpStatus.getCode();
    }

    public AppsException(@NonNull Supplier<String> message, @NonNull Throwable cause) {
        super(message.get(), cause);
        errorCode = new ErrorCode();
        this.rootCause = cause;
        this.httpStatus = 500;
    }

    public AppsException(@NonNull Supplier<String> message, @NonNull Throwable cause, int internalCode) {
        super(message.get(), cause);
        errorCode = new ErrorCode(internalCode);
        this.rootCause = cause;
        this.httpStatus = 500;
    }

    public AppsException(@NonNull Supplier<String> message, @NonNull Throwable cause, int internalCode, @NonNull HttpStatus httpStatus) {
        super(message.get(), cause);
        errorCode = new ErrorCode(internalCode);
        this.rootCause = cause;
        this.httpStatus = httpStatus.getCode();
    }

    /**
     * Конструктор когда мы проверяем ошибку
     * например на null и кадаем AppsException вместо
     * NullPointerExc
     *
     * @param message сообщение
     * @param clazz   ошибка какая могла бы появится если бы не проверка
     *                в основном использкется в Utility классах
     */
    public AppsException(@NonNull String message, @NonNull Class clazz) {
        super(message);
        this.errorCode = new ErrorCode();
        this.rootCause = null;
        this.aClass = clazz;
        this.httpStatus = 500;
    }
}