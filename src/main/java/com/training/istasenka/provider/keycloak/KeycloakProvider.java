package com.training.istasenka.provider.keycloak;

import com.training.istasenka.dto.user.UserLoginDto;
import org.keycloak.admin.client.Keycloak;

public interface KeycloakProvider {
    Keycloak getKeycloakClientCredential();
    Keycloak getKeycloakPassword(UserLoginDto user);
    Keycloak getKeycloakRefresh(String refreshToken);
}
