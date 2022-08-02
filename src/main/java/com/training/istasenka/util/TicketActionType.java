package com.training.istasenka.util;

import static com.training.istasenka.util.StatusType.*;

public enum TicketActionType {
    CREATE(DRAFT),
    SUBMIT(NEW, DRAFT, DECLINED),
    APPROVE(APPROVED, NEW),
    DECLINE(DECLINED, NEW),
    CANCEL(CANCELED, NEW, DRAFT, APPROVED, DECLINED),
    ASSIGN_TO_ME(IN_PROGRESS, APPROVED),
    DONE(StatusType.DONE, IN_PROGRESS);

    private final StatusType[] currentStatuses;
    private final StatusType newStatus;

    TicketActionType(StatusType newStatus, StatusType... currentStatuses) {
        this.currentStatuses = currentStatuses;
        this.newStatus = newStatus;
    }

    public StatusType[] getCurrentStatuses() {
        return currentStatuses;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }
}
