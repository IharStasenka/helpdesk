package com.training.istasenka.dto.user;

import com.training.istasenka.validator.user.Password;
import lombok.*;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserLoginDto {
    @Email
    private String username;
    @Password
    private String password;
}
