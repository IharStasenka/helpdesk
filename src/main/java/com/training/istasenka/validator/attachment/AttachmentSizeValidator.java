package com.training.istasenka.validator.attachment;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AttachmentSizeValidator implements ConstraintValidator<AttachmentSize, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return (multipartFile.getSize() <= 5242880);
    }
}
