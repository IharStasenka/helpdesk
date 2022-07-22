package com.training.istasenka.service.user.keycloak;

import com.training.istasenka.converter.user.UserKeycloakConverter;
import com.training.istasenka.model.user.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    private final UserKeycloakConverter userKeycloakConverter;
    private final Keycloak keycloak;
    private @Value("${keycloak.realm}") String realm;


    @Override
    public void postUser(User user) {
        keycloak.realm(""). users().create(userKeycloakConverter.convertUserToKeycloakUser(user));
    }
}
