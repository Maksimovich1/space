package by.mmb.service.validation;

/**
 * Интерфейс для валидации объектов
 * Нужна фабпика или что то вроде этого чьл бы избежать Qualifier
 *
 * @author andrew.maksimovich
 */
public interface ValidationObject<T> {
    boolean isValid(T t);
}
