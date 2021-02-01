package by.mmb.exception;

import java.util.function.Supplier;

/**
 * Утилитный класс где будут инкапсулироваться
 * разные шаблоны
 *
 * @author andrew.maksimovich
 */
public final class ExceptionUtility {

    private ExceptionUtility() throws AppsException {
        throw new AppsException(() -> "Запрещено создавать экземпляр этого класса!");
    }

    /**
     * Метод для проверки на определенную ошибку. Оборачивем в Supplier что бы память для сообщения
     * была выделена лениво
     *
     * @param o       объект для проверки
     * @param message Supplier с сообщением внутри
     * @param <T>     тип ошибки
     * @throws AppsException
     */
    public static <T> void checkException(Object o, Class<T> tClass, Supplier<String> message) throws AppsException {
        if (NullPointerException.class.equals(tClass)) {
            if (o == null)
                throw new AppsException(message.get(), tClass);
        }
    }
}