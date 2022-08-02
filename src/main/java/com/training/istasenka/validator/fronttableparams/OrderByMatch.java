package com.training.istasenka.validator.fronttableparams;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = OrderByValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface OrderByMatch{

    Class<?> value();

    String message() default "{Incorrect orderBy data value}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
