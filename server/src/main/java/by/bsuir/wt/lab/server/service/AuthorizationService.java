package by.bsuir.wt.lab.server.service;

import by.bsuir.wt.lab.server.model.AuthType;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationService {

    private static final AuthorizationService INSTANCE = new AuthorizationService();

    private final Map<Object, AuthType> users;

    private AuthorizationService() {
        users = new HashMap<>();
    }

    public static AuthorizationService getInstance() {
        return INSTANCE;
    }

    public AuthType getAuthType(Object user) {
        if (!users.containsKey(user)) {
            users.put(user, AuthType.UNAUTHORIZED);
        }

        return users.get(user);
    }

    public void setAuthType(Object user, AuthType type) {
        users.put(user, type);
    }

}