package com.training.istasenka.util;

public enum TokenType {
    ACCESS("A"),
    REFRESH("R");

    private final String tokenTypeString;

    TokenType(String shortTokenTypeName) {
        this.tokenTypeString = shortTokenTypeName;
    }

    public String getShortTokenTypeName() {
        return tokenTypeString;
    }

    public static TokenType getFullTokenTypeName (String tokenTypeShortName) {
        switch (tokenTypeShortName) {
            case "A":
                return TokenType.ACCESS;
            case "R":
                return TokenType.REFRESH;
            default:
                throw new IllegalArgumentException("not supported" + tokenTypeShortName);
        }
    }

    public static TokenType getTokenTypeByFullName(String tokenTypeName) {
        switch (tokenTypeName) {
            case "ACCESS":
                return TokenType.ACCESS;
            case "REFRESH":
                return TokenType.REFRESH;
            default:
                throw new IllegalArgumentException("not supported" + tokenTypeName);
        }
    }
}
