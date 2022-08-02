package com.training.istasenka.converter.user;

import com.training.istasenka.dto.user.UserDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(UserKeycloakConverterDecorator.class)
public interface UserKeycloakConverter {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "email")
    UserRepresentation convertUserToKeycloakUser(UserDto userDto);
}
