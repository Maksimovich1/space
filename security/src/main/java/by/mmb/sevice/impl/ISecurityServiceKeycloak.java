package by.mmb.sevice.impl;

import by.mmb.model.User;
import by.mmb.sevice.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ISecurityServiceKeycloak implements SecurityService {
    @Override
    public User getCurrentUser() {
        AccessToken token = getAccessToken();

        return User.builder()
                .id(token.getSubject())
                .name(token.getName())
                .email(token.getEmail())
                .additionalParam(null)
                .build();
    }

    private AccessToken getAccessToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
            AccessToken token = account.getKeycloakSecurityContext().getToken();
            Objects.requireNonNull(token, "token dont must be null!");
            return token;
        } catch (Exception ex) {
            // todo custom exception
            log.error("Не удалось получить юзера! ", ex);
            throw ex;
        }
    }

    @Override
    public String getUserId() {
        return getAccessToken().getId();
    }
}
