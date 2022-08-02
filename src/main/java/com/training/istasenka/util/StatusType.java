package com.training.istasenka.util;

public enum StatusType {
    DRAFT("DR"),
    NEW("NE"),
    APPROVED("AP"),
    DECLINED("DE"),
    IN_PROGRESS("IN"),
    DONE("DO"),
    CANCELED("CA");

    private final String statusString;

    StatusType(String shortStatusName) {
        this.statusString = shortStatusName;
    }

    public String getShortStatusTypeName() {
        return statusString;
    }

    public static StatusType getFullStatusTypeName(String statusShortName) {
        switch (statusShortName) {
            case "DR":
                return StatusType.DRAFT;
            case "NE":
                return StatusType.NEW;
            case "AP":
                return StatusType.APPROVED;
            case "DE":
                return StatusType.DECLINED;
            case "IN":
                return StatusType.IN_PROGRESS;
            case "DO":
                return StatusType.DONE;
            case "CA":
                return StatusType.CANCELED;
            default:
                throw new IllegalArgumentException("not supported short status name" + statusShortName);
        }
    }
}
