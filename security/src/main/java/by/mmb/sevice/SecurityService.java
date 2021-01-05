package by.mmb.sevice;

import by.mmb.exception.AppsException;
import by.mmb.model.User;

/**
 * @author andrew maksimovich
 */
public interface SecurityService {
    User getCurrentUser() throws AppsException;

    String getUserId() throws AppsException;
}