package com.training.istasenka.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class TokenBlacklistedException extends JWTVerificationException {

    public TokenBlacklistedException(String message) {
        super(message);
    }

}
