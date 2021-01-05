package by.mmb.sevice.impl;

import by.mmb.exception.AppsException;
import by.mmb.model.User;
import by.mmb.sevice.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Slf4j
public class ISecurityServiceKeycloak implements SecurityService {
    @Override
    public User getCurrentUser() throws AppsException {
        AccessToken token = getAccessToken();
        log.trace("the token was successfully received");
        return User.builder()
                .id(token.getSubject())
                .name(token.getName())
                .email(token.getEmail())
                .additionalParam(null)
                .build();
    }

    private AccessToken getAccessToken() throws AppsException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
            AccessToken token = account.getKeycloakSecurityContext().getToken();
            Objects.requireNonNull(token, "token dont must be null!");
            return token;
        } catch (Exception ex) {
            throw new AppsException("Не удалось получить юзера", ex, -5000);
        }
    }

    @Override
    public String getUserId() throws AppsException {
        String id = getAccessToken().getId();
        if (!StringUtils.hasText(id)) {
            throw new AppsException("Был получен не вылидный id = " + id, -5001);
        }
        return id;
    }
}
