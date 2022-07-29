package com.training.istasenka.service.user.keycloak;

import com.training.istasenka.converter.user.UserKeycloakConverter;
import com.training.istasenka.dto.user.PasswordChange;
import com.training.istasenka.dto.user.UserLoginDto;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.model.user.User;
import com.training.istasenka.provider.cacheuserkey.ContextUsernameProvider;
import com.training.istasenka.provider.keycloak.KeycloakProvider;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    private final UserKeycloakConverter userKeycloakConverter;
    private final KeycloakProvider keycloakProvider;
    private final ContextUsernameProvider contextUsernameProvider;
    private @Value("${keycloak.realm}")
    String realm;

    @Override
    public void postUser(User user) {
        var keycloak = keycloakProvider.getKeycloakClientCredential();
        var userRepresentation = userKeycloakConverter.convertUserToKeycloakUser(user);
        try (var response = keycloak.realm(realm).users().create(userRepresentation)) {
            var statusInfo = response.getStatusInfo();
            processKeycloakResponse(statusInfo);
        }
    }

    @Override
    public void deleteUser(User user) {
        var keycloak = keycloakProvider.getKeycloakClientCredential();
        var keycloakUser = getUserRepresentation(user.getEmail(), keycloak);
        keycloak.tokenManager().getAccessToken();
        try (var response = keycloak.realm(realm).users().delete(keycloakUser.getId())) {
            var statusInfo = response.getStatusInfo();
            processKeycloakResponse(statusInfo);
        }
    }

    @Override
    public AccessTokenResponse getAccessToken(UserLoginDto userLoginDto) {
        var keycloak = keycloakProvider.getKeycloakPassword(userLoginDto);
        var token = keycloak.tokenManager().getAccessToken();
        keycloak.close();
        return token;
    }

    @Override
    public void changePassword(PasswordChange passwordChangeData, String username) {
        if (username == null || !username.equals(contextUsernameProvider.getUsername())) {
            throw new CustomIllegalArgumentException("Mismatch of context username, and username in path variable");
        }
        var userLoginDto = getUserLoginDto(passwordChangeData, username);
        var accessToken = getAccessToken(userLoginDto);
        var keycloak = keycloakProvider.getKeycloakClientCredential();
        var userRepresentation = getUserRepresentation(username, keycloak);
        var keycloakUser = keycloak.realm(realm).users().get(userRepresentation.getId());
        var credential = getCredentialRepresentation(passwordChangeData);
        keycloakUser.resetPassword(credential);
        keycloak.tokenManager().invalidate(accessToken.getToken());
        keycloak.tokenManager().invalidate(accessToken.getRefreshToken());
        keycloakUser.logout();
    }

    private UserLoginDto getUserLoginDto(PasswordChange passwordChangeData, String username) {
        return UserLoginDto.builder().username(username).password(passwordChangeData.getOldPassword()).build();
    }

    private CredentialRepresentation getCredentialRepresentation(PasswordChange passwordChangeData) {
        var credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(passwordChangeData.getNewPassword());
        return credential;
    }

    private void processKeycloakResponse(Response.StatusType statusInfo) {
        if (statusInfo.getFamily() != Family.SUCCESSFUL) {
            throw new HttpResponseException(
                    "Keycloak exception with code " + statusInfo.getStatusCode() + ": " + statusInfo.getReasonPhrase(),
                    statusInfo.getStatusCode(),
                    statusInfo.getReasonPhrase(),
                    statusInfo.getReasonPhrase().getBytes());
        }
    }

    private UserRepresentation getUserRepresentation(String username, Keycloak keycloak) {
        return keycloak.realm(realm).users().search(username).stream().findFirst().orElseThrow();
    }
}
