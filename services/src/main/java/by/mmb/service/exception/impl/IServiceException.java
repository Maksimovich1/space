package by.mmb.service.exception.impl;

import by.mmb.HttpStatus;
import by.mmb.code.ErrorCode;
import by.mmb.exception.AppsException;
import by.mmb.repo.exception.ExceptionRepository;
import by.mmb.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис по работе с ошибками
 *
 * @author andrew.maksimovich
 */
@Slf4j
@Service
public class IServiceException implements ServiceException {

    private final ExceptionRepository exceptionRepository;

    @Autowired
    public IServiceException(ExceptionRepository exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    @Override
    public String getMessageForClientByInternalCode(int internalCode) throws AppsException {
        checkValidInternalCode(internalCode);
        ErrorCode errorCode = exceptionRepository.getErrorCodeByCode(internalCode);
        return errorCode.getMessageForClient();
    }

    private void checkValidInternalCode(int internalCode) throws AppsException {
        if (internalCode >= 0) {
            throw new AppsException(() -> "Не валидный код ошибки = " + internalCode, -11111, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getMessageForClientByExternalCode(int externalCode) {
        return null;
    }

    @Override
    public ErrorCode getErrorCodeByInternalCode(int internalCode) throws AppsException {
        checkValidInternalCode(internalCode);
        try {
            return exceptionRepository.getErrorCodeByCode(internalCode);
        } catch (Exception ex) {
            throw new AppsException(() -> "Error", ex, -10111);
        }
    }
}
