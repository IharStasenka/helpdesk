package com.training.istasenka.validator.user;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidatorImpl implements UserValidator {
    private static final  String EMAIL_REGEX
            = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASS_REGEX
            = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


    @Override
    public Boolean isPasswordValid(String password) {
        final Pattern pattern = Pattern.compile(PASS_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public Boolean isUsernameValid(String username) {
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
