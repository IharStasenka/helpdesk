package com.training.istasenka.util;

import static com.training.istasenka.util.TicketActionType.*;

public enum UserRole {
    EMPLOYEE("EMP", SUBMIT, CANCEL),
    MANAGER("MAN", SUBMIT, CANCEL, APPROVE, DECLINE),
    ENGINEER("ENG", ASSIGN_TO_ME, CANCEL, DONE);

    private final String shortRoleName;
    private final TicketActionType[] actions;


    UserRole(String shortRoleName, TicketActionType... actions) {
        this.shortRoleName = shortRoleName;
        this.actions = actions;
    }

    public String getShortRoleName() {
        return shortRoleName;
    }

    public TicketActionType[] getActions() {
        return actions;
    }

    public static UserRole fromShortUserName (String roleShortName) {
     switch (roleShortName) {
         case "EMP":
             return UserRole.EMPLOYEE;
         case "MAN":
             return UserRole.MANAGER;
         case "ENG":
             return UserRole.ENGINEER;
         default:
             throw new IllegalArgumentException("not supported" + roleShortName);
     }

    }
}
