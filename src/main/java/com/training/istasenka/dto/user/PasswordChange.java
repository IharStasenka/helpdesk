package com.training.istasenka.dto.user;

import com.training.istasenka.validator.user.Password;
import com.training.istasenka.validator.user.PasswordsMatch;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@PasswordsMatch(
        password = "newPassword",
        confirmPassword = "confirmation",
        message = "Passwords must be equal"
)
public class PasswordChange {
    @Password
    private String oldPassword;
    @Password
    private String newPassword;
    private String confirmation;
}
