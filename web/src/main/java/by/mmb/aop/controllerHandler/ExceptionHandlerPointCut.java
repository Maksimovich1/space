package by.mmb.aop.controllerHandler;

import by.mmb.exception.AppsException;
import by.mmb.service.exception.ProcessedException;
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

    private final ProcessedException processedException;

    @Autowired
    public ExceptionHandlerPointCut(ProcessedException processedException) {
        this.processedException = processedException;
    }

    @Around(value = "@annotation(by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing)")
    public Object around(ProceedingJoinPoint joinPoint) throws AppsException {
        try {
            log.trace("Is work proxy for annotation ExceptionHandlerProcessing");
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.trace("advice working around of restcontroller method " + joinPoint.getKind());
            return processedException.handlerException(throwable);
        }
    }
}