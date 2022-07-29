package com.training.istasenka.provider.keycloak;

import com.training.istasenka.dto.user.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.keycloak.OAuth2Constants.*;

@Component
@RequiredArgsConstructor
public class KeycloakProviderImpl implements KeycloakProvider {
    @Value("${keycloak.auth-server-url}")
    private String url;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.credentials.secret}")
    private String secret;


    @Override
    public Keycloak getKeycloakClientCredential() {
        return KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(url)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secret)
                .build();
    }

    @Override
    public Keycloak getKeycloakPassword(UserLoginDto user) {
        return KeycloakBuilder.builder()
                .grantType(PASSWORD)
                .serverUrl(url)
                .username(user.getUsername())
                .password(user.getPassword())
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secret)
                .build();
    }

    @Override
    public Keycloak getKeycloakRefresh(String refreshToken) {
        return KeycloakBuilder.builder()
                .grantType(REFRESH_TOKEN)
                .authorization(refreshToken)
                .serverUrl(url)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secret)
                .build();
    }
}
