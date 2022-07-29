package com.training.istasenka.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordsMatch {
    String message() default "Fields values don't match!";
    String password();
    String confirmPassword();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
