package by.mmb.exception;

import by.mmb.code.ErrorCode;

/**
 * The AppsException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages.
 *
 * @author andrew.maksimovich
 */
public class AppsException extends Exception {

    private static final long serialVersionUID = 7718828512143293558L;

    private final ErrorCode errorCode;

    private final Throwable rootCause;

    public AppsException(String message, int code) {
        super(message);
        errorCode = new ErrorCode(code);
        this.rootCause = null;
    }

    public AppsException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.rootCause = cause;
    }

    public AppsException(String message, Throwable cause, int code) {
        errorCode = new ErrorCode(code);
        this.rootCause = cause;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Throwable getRootCause() {
        return rootCause;
    }
}
