package by.mmb.service.exception;

import by.mmb.exception.AppsException;

/**
 * @author andrew.maksimovich
 */
public interface ProcessedException {
    Object handlerException(Throwable throwable) throws AppsException;
}