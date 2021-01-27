package by.mmb.exception;

import lombok.NonNull;

/**
 * Ошибка которая генерироваться в инфоструктуре.
 * Нарпример в аспекте, генерируем эту ошибку передавая в нее оригинальную ошибку
 * что бы потом при обработке достать всю нужную информацию
 *
 * @author andrew.maksimovich
 */
public class AutoAppsException extends AppsException {

    public AutoAppsException(@NonNull String message, @NonNull Throwable cause, int internalCode) {
        super(message, cause, internalCode);
    }
}