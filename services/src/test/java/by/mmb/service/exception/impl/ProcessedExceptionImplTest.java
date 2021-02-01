package by.mmb.service.exception.impl;

import by.mmb.code.ErrorCode;
import by.mmb.exception.AppsException;
import by.mmb.exception.AutoAppsException;
import by.mmb.service.exception.ProcessedException;
import by.mmb.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProcessedExceptionImplTest {

    @Mock
    ServiceException serviceException;

    private String errMess = "Errors!";
    private ErrorCode errorCode = new ErrorCode(-9999, -19999, "Error", "EEE");

    /**
     * стандартная неизвестная ошибка
     */
    private ErrorCode standartExc = new ErrorCode(-9999, -19999, "Error", "EEE");


    @Test
    void handlerException_throw_unknown_exception() throws AppsException {

        Mockito.lenient().when(serviceException.getErrorCodeByInternalCode(-5555)).thenReturn(errorCode);
        ProcessedException processedException = new ProcessedExceptionImpl(serviceException);

        assertThrows(AutoAppsException.class,
                () -> {
                    processedException.handlerException(new NullPointerException(errMess));
                });

        try {
            processedException.handlerException(new NullPointerException(errMess));
        } catch (AutoAppsException throwable) {
            assertEquals(throwable.getMessage(), errMess);
            assertNotNull(throwable.getErrorCode());
            assertNotNull(throwable.getRootCause());
            assertEquals(throwable.getRootCause().getClass(), NullPointerException.class);
            assertEquals(standartExc.getExternalCode(), throwable.getErrorCode().getExternalCode());
            assertEquals(standartExc.getInternalCode(), throwable.getErrorCode().getInternalCode());
        }
    }

    @Test
    void handlerException_throw_known_exception() throws AppsException {
        String customErr = "Что то пошло не так";
        Mockito.lenient().when(serviceException.getErrorCodeByInternalCode(-15555)).thenReturn(errorCode);
        AppsException appsException = new AppsException(() -> customErr, new NullPointerException(errMess), -15555);

        ProcessedException processedException = new ProcessedExceptionImpl(serviceException);

        assertThrows(AppsException.class,
                () -> {
                    processedException.handlerException(appsException);
                });


        try {
            processedException.handlerException(appsException);
        } catch (AppsException throwable) {
            assertEquals(throwable.getMessage(), customErr);
            assertNotNull(throwable.getErrorCode());
            assertNotNull(throwable.getRootCause());
            assertEquals(throwable.getRootCause().getClass(), NullPointerException.class);
            assertEquals(-9999, throwable.getErrorCode().getExternalCode());
            assertEquals(-15555, throwable.getErrorCode().getInternalCode());
            assertEquals(errorCode.getMessageForClient(), throwable.getErrorCode().getMessageForClient());
            assertEquals(errorCode.getMessage(), throwable.getErrorCode().getMessage());
        }
    }
}