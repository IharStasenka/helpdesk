package com.training.istasenka.assembler;

import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.model.user.User;
import com.training.istasenka.provider.link.LinkProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {

    private final UserConverter userConverter;
    private final LinkProvider linkProvider;

    public UserDtoAssembler(UserConverter userConverter, LinkProvider linkProvider) {
        super(UserConverter.class, UserDto.class);
        this.userConverter = userConverter;
        this.linkProvider = linkProvider;
    }

    @Override
    @NonNull
    public UserDto toModel(@NonNull User entity) {
        var userDto = userConverter.convertUserToUserDto(entity);
        userDto.add(linkProvider.getUserLink(entity.getEmail()));
        userDto.add(linkProvider.getDefaultTicketPageLink());
        return userDto;
    }


}
