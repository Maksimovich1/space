package by.mmb.exception;

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

    public AppsException(@NonNull String message, int code) {
        super(message);
        errorCode = new ErrorCode(code);
        this.rootCause = null;
    }

    public AppsException(@NonNull Supplier<String> message, int code) {
        super(message.get());
        errorCode = new ErrorCode(code);
        this.rootCause = null;
    }

    public AppsException(@NonNull String message, @NonNull Throwable cause, @NonNull ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.rootCause = cause;
    }

    public AppsException(@NonNull String message, @NonNull Throwable cause, int code) {
        super(message, cause);
        errorCode = new ErrorCode(code);
        this.rootCause = cause;
    }

    public AppsException(@NonNull String message) {
        super(message);
        this.errorCode = new ErrorCode();
        this.rootCause = null;
    }

    public AppsException(@NonNull Supplier<String> message) {
        super(message.get());
        this.errorCode = new ErrorCode();
        this.rootCause = null;
    }

    public AppsException(@NonNull String message, @NonNull Class clazz) {
        super(message);
        this.errorCode = new ErrorCode();
        this.rootCause = null;
        this.aClass = clazz;
    }

    public AppsException(Supplier<String> message, Exception ex) {
        super(message.get());
        this.errorCode = new ErrorCode();
        this.rootCause = ex;
    }
}
