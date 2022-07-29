package com.training.istasenka.validator.user;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Password {
    String message() default "{The provided password has incorrect format.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
