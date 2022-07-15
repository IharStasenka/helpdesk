package com.training.istasenka.converter.user;

import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDto convertUserToUserDto(User user);

    User convertUserDtoToUser(UserDto userDto);
}
