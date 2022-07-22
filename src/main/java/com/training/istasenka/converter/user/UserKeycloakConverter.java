package com.training.istasenka.converter.user;

import com.training.istasenka.model.user.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(UserKeycloakConverterDecorator.class)
public interface UserKeycloakConverter {

    UserRepresentation convertUserToKeycloakUser(User user);
}
