package com.training.istasenka.util;

public enum UrgencyType  {
    CRITICAL("C"),
    HIGH("H"),
    AVERAGE("A"),
    LOW("L");

    private final String shortUrgencyTypeName;

    UrgencyType(String shortUrgencyName) {
        this.shortUrgencyTypeName = shortUrgencyName;
    }

    public String getShortUrgencyTypeName() {
        return shortUrgencyTypeName;
    }

    public static UrgencyType getUrgencyTypeName (String statusUrgencyName) {
        switch (statusUrgencyName) {
            case "C":
                return UrgencyType.CRITICAL;
            case "H":
                return UrgencyType.HIGH;
            case "A":
                return UrgencyType.AVERAGE;
            case "L":
                return UrgencyType.LOW;
            default:
                throw new IllegalArgumentException("not supported short urgency name" + statusUrgencyName);
        }
    }

}
