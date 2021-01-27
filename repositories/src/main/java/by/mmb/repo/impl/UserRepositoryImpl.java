package by.mmb.repo.impl;

import by.mmb.model.User;
import by.mmb.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author andrew.maksimovich
 */
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getUserByIdKeycloak(String idKeycloak) {
        return User.builder()
                .idUser(1)
                .idKeycloak(idKeycloak)
                .dateReg(LocalDateTime.now())
                .login("admin")
                .build();
    }

    @Override
    public User getUserById(long id) {
        return null;
    }
}
