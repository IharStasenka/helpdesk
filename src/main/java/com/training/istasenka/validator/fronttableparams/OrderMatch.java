package com.training.istasenka.validator.fronttableparams;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = OrderValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)

public @interface OrderMatch {

    String message() default "{Incorrect order value}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
