package com.training.istasenka.assembler;

import com.training.istasenka.controller.TicketController;
import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.model.user.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserDtoAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {

    private final UserConverter userConverter;

    public UserDtoAssembler(UserConverter userConverter) {
        super(UserConverter.class, UserDto.class);
        this.userConverter = userConverter;
    }

    @Override
    @NonNull
    public UserDto toModel(@NonNull  User entity) {
        var userDto = userConverter.convertUserToUserDto(entity);
        userDto.add(linkTo(getControllerClass()).slash(entity.getEmail()).withSelfRel());
        userDto.add(linkTo(TicketController.class).withRel("tickets"));
        return userDto;
    }


}
