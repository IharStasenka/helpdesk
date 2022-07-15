package com.training.istasenka.util;

public enum CategoryType {
    AS ("Application & Services"),
    BPW ("Benefits & Paper Work"),
    HS ("Hardware & Software"),
    PM ("People Management"),
    SA ("Security & Access"),
    WF ("Workplaces & Facilities");

    private final String type;

    CategoryType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
