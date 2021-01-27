package by.mmb.exception;

import lombok.NonNull;

import java.util.function.Supplier;

/**
 * Ошибка которая генерироваться в инфоструктуре.
 * Нарпример в аспекте, генерируем эту ошибку передавая в нее оригинальную ошибку
 * что бы потом при обработке достать всю нужную информацию
 *
 * @author andrew.maksimovich
 */
public class AutoAppsException extends AppsException {

    public AutoAppsException(@NonNull Supplier<String> message, @NonNull Throwable cause, int internalCode) {
        super(message.get(), cause, internalCode);
    }
}