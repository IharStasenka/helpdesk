package com.training.istasenka.util;

public enum HistoryActionType {

    TICKET_CREATED("Ticket is created", "Ticket is created"),
    TICKET_UPDATED("Ticket is edited", "Ticket is edited"),
    TICKET_STATUS_CHANGE("Ticket status is changed", "Ticket status is changed from : %s to %s"),
    ATTACHMENT_ADDED("File is attached", "File is attached: %s"),
    ATTACHMENT_REMOVED("File is removed", "File is removed: %s");

    private final String actionMessage;
    private final String descriptionMessageFormat;

    HistoryActionType(String actionMessage, String descriptionMessageFormat) {
        this.actionMessage = actionMessage;
        this.descriptionMessageFormat = descriptionMessageFormat;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public String getDescriptionMessageFormat() {
        return descriptionMessageFormat;
    }
}
