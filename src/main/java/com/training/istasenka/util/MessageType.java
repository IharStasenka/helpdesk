package com.training.istasenka.util;

public enum MessageType {
    ERROR ("error"),
    INFO ("info");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
