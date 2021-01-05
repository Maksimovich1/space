package by.mmb.controllers.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для ендпоитов, что бы
 * конторировать выбрасываемые эксепшены
 *
 * @author andrew.maksimovich
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionHandlerProcessing {
}