package by.mmb.sevice.impl;

import by.mmb.exception.AppsException;
import by.mmb.exception.ExceptionUtility;
import by.mmb.model.User;
import by.mmb.repo.UserRepository;
import by.mmb.sevice.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ISecurityServiceKeycloak implements SecurityService {

    private final UserRepository userRepository;

    @Autowired
    public ISecurityServiceKeycloak(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() throws AppsException {
        //TODO !!!!!!!!!!!
        //AccessToken token = getAccessToken();
//        User userByIdKeycloak = userRepository.getUserByIdKeycloak(token.getSubject());
        User userByIdKeycloak = userRepository.getUserByIdKeycloak("qwe123qwe");
        log.trace("the token was successfully received");
        return userByIdKeycloak;
    }

    private AccessToken getAccessToken() throws AppsException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
            AccessToken token = account.getKeycloakSecurityContext().getToken();
            ExceptionUtility.checkException(token, NullPointerException.class, () -> "token dont must be null!");
            return token;
        } catch (Exception ex) {
            throw new AppsException(() -> "Не удалось получить юзера", ex);
        }
    }

    @Override
    public String getUserIdKeycloak() throws AppsException {
        String id = getAccessToken().getSubject();
        if (!StringUtils.hasText(id)) {
            throw new AppsException(() -> "Был получен не вылидный id = " + id);
        }
        return id;
    }
}
