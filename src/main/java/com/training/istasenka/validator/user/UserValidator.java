package com.training.istasenka.validator.user;

public interface UserValidator {
    Boolean isPasswordValid(String password);
    Boolean isUsernameValid(String username);
}
