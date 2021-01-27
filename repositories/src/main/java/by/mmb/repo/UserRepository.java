package by.mmb.repo;

import by.mmb.model.User;

/**
 * @author andrew.maksimovich
 */
public interface UserRepository {
    User getUserByIdKeycloak(String idKeycloak);

    User getUserById(long id);
}