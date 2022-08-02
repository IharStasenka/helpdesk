package com.training.istasenka.validator.textfield;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFieldValidator implements ConstraintValidator<TextFieldMatch, String> {

    private Pattern textRegex;


    @Override
    public void initialize(TextFieldMatch constraintAnnotation) {
        textRegex = Pattern.compile("^$|^[(a-z)(A-Z)(0-9)( ~.\\\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|})]+$");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null ) {
            return true;
        }
        Matcher matcher = textRegex.matcher(s);
        return matcher.matches();
    }
}
