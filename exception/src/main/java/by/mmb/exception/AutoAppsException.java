package by.mmb.exception;

/**
 * Ошибка которая генерироваться в инфоструктуре.
 * Нарпример в аспекте, генерируем эту ошибку передавая в нее оригинальную ошибку
 * что бы потом при обработке достать всю нужную информацию
 *
 * @author andrew.maksimovich
 */
public class AutoAppsException extends AppsException {

    public AutoAppsException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }
}