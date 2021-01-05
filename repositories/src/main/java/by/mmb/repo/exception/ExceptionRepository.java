package by.mmb.repo.exception;

import by.mmb.code.ErrorCode;

/**
 * Репозиторий по работе с ошибками
 *
 * @author andrew.maksimovich
 */
public interface ExceptionRepository {

    ErrorCode getErrorCodeByCode(int internalCode);
}