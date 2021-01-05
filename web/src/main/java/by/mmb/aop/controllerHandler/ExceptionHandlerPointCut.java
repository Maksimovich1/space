package by.mmb.aop.controllerHandler;

import by.mmb.code.ErrorCode;
import by.mmb.exception.AppsException;
import by.mmb.exception.AutoAppsException;
import by.mmb.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author andrew.maksimovich
 */
@Aspect
@Component
@Slf4j
public class ExceptionHandlerPointCut {

    private final ServiceException serviceException;

    @Autowired
    public ExceptionHandlerPointCut(ServiceException serviceException) {
        this.serviceException = serviceException;
    }

    @Around(value = "@annotation(by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing)")
    public Object around(ProceedingJoinPoint joinPoint) throws AppsException {
        try {
            log.trace("Is work proxy");
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.trace("advice working around of restcontroller method " + joinPoint.getKind());
            return checkNeedWrapException(throwable);
        }
    }

    private Object checkNeedWrapException(Throwable throwable) throws AppsException {
        if (throwable instanceof AppsException) {
            throw configureAppsException((AppsException) throwable);
        } else {
            log.trace("convert " + throwable.getClass().getSimpleName() + " to AppsException.");
            throw new AutoAppsException(throwable.getMessage(), throwable, -9999);
        }
    }

    /**
     * Настраиваем ошибку для читабельности
     *
     * @param throwable исходная ошибка
     * @return ошибка которая пойдет на контроллер advice
     */
    private AppsException configureAppsException(AppsException throwable) {
        AppsException appsEx = throwable;
        ErrorCode errorCode = appsEx.getErrorCode();
        if (errorCode != null) {
            int internalCode = errorCode.getInternalCode();
            ErrorCode errorCodeByInternalCode;
            try {
                errorCodeByInternalCode = serviceException.getErrorCodeByInternalCode(internalCode);
            } catch (AppsException e) {
                // нарушена структура ошибки
                log.error("Нарушена страктура ошибки, информация может быть не полной");
                appsEx = new AutoAppsException("Ошибка при разборе ошибки!", appsEx, -1);
                return appsEx;
            }
            appsEx.getErrorCode().setMessage(errorCodeByInternalCode.getMessage());
            appsEx.getErrorCode().setMessageForClient(errorCodeByInternalCode.getMessageForClient());
            appsEx.getErrorCode().setExternalCode(errorCodeByInternalCode.getExternalCode());
        } else {
            // если каким то чудом здесь то нужно разбираться
            log.error("В AppsException поле errorCode = null!!");
            appsEx = new AutoAppsException("Ошибка при разборе ошибки!", appsEx, -1);
        }
        return appsEx;
    }
}