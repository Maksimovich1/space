package by.mmb.service.exception;

import by.mmb.code.ErrorCode;
import by.mmb.exception.AppsException;

/**
 * @author andrew.maksimovich
 */
public interface ServiceException {

    String getMessageForClientByInternalCode(int internalCode) throws AppsException;

    String getMessageForClientByExternalCode(int externalCode);

    ErrorCode getErrorCodeByInternalCode(int internalCode) throws AppsException;
}