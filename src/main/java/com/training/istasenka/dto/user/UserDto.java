package com.training.istasenka.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.ticket.View;
import com.training.istasenka.util.UserRole;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDto extends RepresentationModel<UserDto> {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @JsonView({View.FullTicket.class})
    private String lastName;

    @NotNull
    @JsonView({View.FullTicket.class})
    private String firstName;

    @NotNull
    @JsonView({View.FullTicket.class})
    private UserRole role;
}
