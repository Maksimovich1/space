package by.mmb.sevice;

import by.mmb.model.User;

public interface SecurityService {
    User getCurrentUser();

    String getUserId();
}