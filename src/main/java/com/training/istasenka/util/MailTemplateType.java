package com.training.istasenka.util;

import com.training.istasenka.model.user.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.Optional;

import static com.training.istasenka.util.StatusType.*;

public enum MailTemplateType {

    TEMPLATE_1(NEW, DRAFT, DECLINED) {
        @Override
        public String getMailSubject() {
            return "New ticket for approval";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear Managers,\n" +
                    "\n" +
                    "New ticket %S is waiting for your approval.\n",
                    link.toString());
        }
    },
    TEMPLATE_2(APPROVED, NEW) {
        @Override
        public String getMailSubject() {
            return "Ticket was approved";
        }


        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear Users,\n" +
                    "\n" +
                    "Ticket %s was approved by the Manager.\n",
                    link.toString());
        }
    },
    TEMPLATE_3(DECLINED, NEW){
        @Override
        public String getMailSubject() {
            return  "Ticket was declined";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear %s %s,\n" +
                    "\n" +
                    "Ticket %s was declined by the Manager\n",
                    user.getFirstName(), user.getLastName(), link.toString());
        }
    },
    TEMPLATE_4(CANCELED, NEW) {
        @Override
        public String getMailSubject() {
            return "Ticket was cancelled";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear %s %s,\n" +
                    "\n" +
                    "Ticket %s was canceled by the Manager\n",
                    user.getFirstName(), user.getLastName(), link.toString());
        }
    },
    TEMPLATE_5(CANCELED, APPROVED){
        @Override
        public String getMailSubject() {
            return "Ticket was cancelled";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear Users,\n" +
                    "\n" +
                    "Ticket %s was canceled by the Engineer\n",
                    link.toString());
        }
    },
    TEMPLATE_6(StatusType.DONE, IN_PROGRESS){
        @Override
        public String getMailSubject() {
            return "Ticket was done";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear %s %s,\n" +
                    "\n" +
                    "Ticket %s was done by the Engineer.\n" +
                    "Please provide your feedback by clicking on the ticket ID\n",
                    user.getFirstName(), user.getLastName(), link.toString());
        }
    },
    TEMPLATE_7(StatusType.DONE, StatusType.DONE){
        @Override
        public String getMailSubject() {
            return "Feedback was provided";
        }

        @Override
        public String getMailBody(User user, Link link) {
            return String.format("Dear %s %s,\n" +
                            "\n" +
                            "The feedback was provided on the ticket %s.\n",
                    user.getFirstName(), user.getLastName(), link.toString());
        }
    };

    private final StatusType[] currentStatuses;
    private final StatusType newStatus;

    MailTemplateType(StatusType newStatus, StatusType... currentStatuses) {
        this.currentStatuses = currentStatuses;
        this.newStatus = newStatus;
    }

    public StatusType[] getCurrentStatuses() {
        return currentStatuses;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }

    public String getMailSubject() {
        return Strings.EMPTY;
    }

    public String getMailBody(User user, Link link) {
        return Strings.EMPTY;
    }

    public static Optional<MailTemplateType> getTemplateByStatuses(StatusType newStatus, StatusType oldStatus) {
        return Arrays
                .stream(MailTemplateType.values())
                .filter(mt -> Arrays.asList(mt.getCurrentStatuses()).contains(oldStatus) && mt.getNewStatus().equals(newStatus))
                .findFirst();
    }
}
