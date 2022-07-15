package com.training.istasenka.validator.attachment;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtensionValidator implements ConstraintValidator<ExtensionMatch, MultipartFile> {

    private Pattern extensionRegex;

    @Override
    public void initialize(ExtensionMatch constraintAnnotation) {
         extensionRegex = Pattern.compile("([^\\s]+(\\.(?i)(jpg|doc|docx|jpeg|png|pdf))$)");
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            return true;
        }
        Matcher matcher = extensionRegex.matcher(multipartFile.getOriginalFilename());
        return matcher.matches();
    }
}
