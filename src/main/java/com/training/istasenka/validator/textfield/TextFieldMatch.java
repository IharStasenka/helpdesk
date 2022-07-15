package com.training.istasenka.validator.textfield;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TextFieldValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface TextFieldMatch {
    String message() default "{Incorrect text format value}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
