package com.training.istasenka.validator.attachment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ExtensionValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtensionMatch {
    String message() default "{The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
