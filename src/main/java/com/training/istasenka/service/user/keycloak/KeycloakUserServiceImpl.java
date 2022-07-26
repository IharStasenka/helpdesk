package com.training.istasenka.service.user.keycloak;

import com.training.istasenka.converter.user.UserKeycloakConverter;
import com.training.istasenka.model.user.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    private final UserKeycloakConverter userKeycloakConverter;
    private final Keycloak keycloak;
    private @Value("${keycloak.realm}")
    String realm;

    @Override
    public void postUser(User user) {
        var userRepresentation = userKeycloakConverter.convertUserToKeycloakUser(user);
        try (var response = keycloak.realm(realm).users().create(userRepresentation)) {
            var statusInfo = response.getStatusInfo();
            processKeycloakResponse(statusInfo);
        }
    }

    @Override
    public void deleteUser(User user) {
        var search = getUserRepresentation(user);
        try(var response = keycloak.realm(realm).users().delete(search.getId())) {
            var statusInfo = response.getStatusInfo();
            processKeycloakResponse(statusInfo);
        }
    }

    @Override
    public void changePassword() {
        UserResource userResource = keycloak.realm(realm).users().get("");
        List<CredentialRepresentation> credentials = userResource.credentials();
        userResource.resetPassword(new CredentialRepresentation());
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

    private UserRepresentation getUserRepresentation(User user) {
        return keycloak.realm(realm).users().search(user.getEmail()).stream().findFirst().orElseThrow();
    }
}
