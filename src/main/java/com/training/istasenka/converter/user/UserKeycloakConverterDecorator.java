package com.training.istasenka.converter.user;

import com.training.istasenka.model.user.User;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class UserKeycloakConverterDecorator implements UserKeycloakConverter {

    @Autowired
    @Qualifier("delegate")
    private UserKeycloakConverter delegate;

    @Override
    public UserRepresentation convertUserToKeycloakUser(User user) {
        var userRepresentation = delegate.convertUserToKeycloakUser(user);
        userRepresentation.setCredentials(getCredentials(user));
        userRepresentation.setGroups(List.of(user.getRole().toString()));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private List<CredentialRepresentation> getCredentials(User user) {
        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        return List.of(credentialRepresentation);
    }
}
