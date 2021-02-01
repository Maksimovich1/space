package by.mmb.service.validation;

import by.mmb.exception.AppsException;

/**
 * Интерфейс для валидации объектов
 * Нужна фабпика или что то вроде этого чьл бы избежать Qualifier
 *
 * @author andrew.maksimovich
 */
public interface ValidationObject<T> {
    boolean isValid(T t) throws AppsException;
}
