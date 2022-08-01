package com.training.istasenka.converter.user;

import com.training.istasenka.dto.user.UserDto;
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
    public UserRepresentation convertUserToKeycloakUser(UserDto userDto) {
        var userRepresentation = delegate.convertUserToKeycloakUser(userDto);
        userRepresentation.setCredentials(getCredentials(userDto));
        userRepresentation.setGroups(List.of(userDto.getRole().toString()));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private List<CredentialRepresentation> getCredentials(UserDto userDto) {
        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userDto.getPassword());
        return List.of(credentialRepresentation);
    }
}
