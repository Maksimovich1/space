package by.mmb.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тест ExceptionUtility
 *
 * @author andrew.maksimovich
 */
class ExceptionUtilityTest {
    private String messNull = "NullPointerException";
    private String messArithmeticException = "ArithmeticException";

    @Test
    void checkException_does_throw() {

        assertThrows(AppsException.class,
                () -> {
                    ExceptionUtility.checkException(null, NullPointerException.class, () -> messNull);
                },
                "Ожидался AppsException!!!");
    }

    @Test
    void checkException_does_not_throw_exc() {
        assertDoesNotThrow(
                () -> {
                    ExceptionUtility.checkException(new Object(), NullPointerException.class, () -> messNull);
                });
        assertDoesNotThrow(
                () -> {
                    ExceptionUtility.checkException(null, ArithmeticException.class, () -> messArithmeticException);
                });
    }
}