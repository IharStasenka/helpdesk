package com.training.istasenka.validator.attachment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = AttachmentSizeValidator.class)
public @interface AttachmentSize {
    String message() default "{The size of the attached file should not be greater than 5 Mb. Please select another file.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
