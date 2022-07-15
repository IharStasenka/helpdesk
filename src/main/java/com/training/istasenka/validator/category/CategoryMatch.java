package com.training.istasenka.validator.category;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CategoryValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CategoryMatch {


    String message() default "{Incorrect category type}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
