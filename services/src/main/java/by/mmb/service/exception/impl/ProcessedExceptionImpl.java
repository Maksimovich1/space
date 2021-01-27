package by.mmb.service.exception.impl;

import by.mmb.code.ErrorCode;
import by.mmb.exception.AppsException;
import by.mmb.exception.AutoAppsException;
import by.mmb.service.exception.ProcessedException;
import by.mmb.service.exception.ServiceException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Обработка ошибок
 *
 * @author andrew.maksimovich
 */
@Component
@Slf4j
public class ProcessedExceptionImpl implements ProcessedException {

    private final ServiceException serviceException;

    @Autowired
    public ProcessedExceptionImpl(ServiceException serviceException) {
        this.serviceException = serviceException;
    }

    @Override
    public Object handlerException(@NonNull Throwable throwable) throws AppsException {
        if (throwable instanceof AppsException) {
            throw configureAppsException((AppsException) throwable);
        } else {
            log.trace("convert " + throwable.getClass().getSimpleName() + " to AppsException.");
            throw new AutoAppsException(() -> throwable.getMessage(), throwable, -19999);
        }
    }

    /**
     * Настраиваем ошибку для читабельности
     *
     * @param throwable исходная ошибка
     * @return ошибка которая пойдет на контроллер advice
     */
    private AppsException configureAppsException(@NonNull AppsException throwable) {
        AppsException appsEx = throwable;
        ErrorCode errorCode = appsEx.getErrorCode();
        if (errorCode != null) {
            int internalCode = errorCode.getInternalCode();
            ErrorCode errorCodeByInternalCode;
            try {
                errorCodeByInternalCode = serviceException.getErrorCodeByInternalCode(internalCode);
            } catch (Exception ex) {
                ;
                // нарушена структура ошибки
                log.error("Нарушена страктура ошибки, информация может быть не полной", ex);
                appsEx = new AutoAppsException(() -> "Ошибка при разборе ошибки!", appsEx, -1);
                return appsEx;
            }
            appsEx.getErrorCode().setMessage(errorCodeByInternalCode.getMessage());
            appsEx.getErrorCode().setMessageForClient(errorCodeByInternalCode.getMessageForClient());
            appsEx.getErrorCode().setExternalCode(errorCodeByInternalCode.getExternalCode());
        } else {
            // если каким то чудом здесь то нужно разбираться
            log.error("В AppsException поле errorCode = null!!");
            appsEx = new AutoAppsException(() -> "Ошибка при разборе ошибки!", appsEx, -1);
        }
        return appsEx;
    }
}