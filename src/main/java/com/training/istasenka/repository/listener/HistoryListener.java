package com.training.istasenka.repository.listener;

import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.history.History;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.service.history.audit.HistoryAuditService;
import com.training.istasenka.util.HistoryActionType;
import com.training.istasenka.util.StatusType;
import lombok.AllArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;


@Component
@AllArgsConstructor
public class HistoryListener implements PreInsertEventListener, PreUpdateEventListener, PreDeleteEventListener {

    private final EntityManagerFactory entityManagerFactory;
    private final HistoryAuditService historyAuditService;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_DELETE).appendListener(this);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        var eventEntityClass = event.getEntity().getClass();
        if (eventEntityClass.equals(Ticket.class)) {
            var ticket = (Ticket) event.getEntity();
            var historyActionType = HistoryActionType.TICKET_CREATED;
            var history = buildHistory(ticket,
                    historyActionType.getActionMessage(),
                    String.format(historyActionType.getDescriptionMessageFormat()));
            historyAuditService.getHistoryAuditEvents().add(history);
        } else if (eventEntityClass.equals(Attachment.class)) {
            var attachment = (Attachment) event.getEntity();
            var ticket = attachment.getTicket();
            var historyActionType = HistoryActionType.ATTACHMENT_ADDED;
            var history = buildHistory(ticket,
                    historyActionType.getActionMessage(),
                    String.format(historyActionType.getDescriptionMessageFormat(), attachment.getName()));
            historyAuditService.getHistoryAuditEvents().add(history);
        }
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        if (preUpdateEvent.getEntity().getClass().equals(Ticket.class)) {
            var ticket = (Ticket) preUpdateEvent.getEntity();
            var oldState = preUpdateEvent.getOldState();
            var newState = preUpdateEvent.getState();
            processTicketStatusChange(ticket, oldState);
            processTicketUpdate(ticket, oldState, newState);
        }
        return false;
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        var eventEntityClass = event.getEntity().getClass();
        if (eventEntityClass.equals(Attachment.class)) {
            var attachment = (Attachment) event.getEntity();
            var ticket = attachment.getTicket();
            var historyActionType = HistoryActionType.ATTACHMENT_REMOVED;
            var history = buildHistory(ticket,
                    historyActionType.getActionMessage(),
                    String.format(historyActionType.getDescriptionMessageFormat(), attachment.getName()));
            historyAuditService.getHistoryAuditEvents().add(history);
        }
        return false;
    }

    private void processTicketUpdate(Ticket ticket, Object[] oldState, Object[] newState) {
        for (int i = 0; i < oldState.length; i++) {
            Object o = oldState[i];
            var historyActionType = HistoryActionType.TICKET_UPDATED;
            if (((oldState[i] != null) && !oldState[i].equals(newState[i])) && !isStatusType(o)) {
                var history = buildHistory(ticket,
                        historyActionType.getActionMessage(),
                        String.format(historyActionType.getDescriptionMessageFormat()));
                historyAuditService.getHistoryAuditEvents().add(history);
                return;
            }
        }

    }

    private void processTicketStatusChange(Ticket ticket, Object[] oldState) {
        for (Object o : oldState) {
            if (isStatusType(o)) {
                var oldStatus = (StatusType) o;
                var newStatus = ticket.getStatus();
                var historyActionType = HistoryActionType.TICKET_STATUS_CHANGE;
                if (!newStatus.equals(oldStatus)) {
                    var history = buildHistory(ticket,
                            historyActionType.getActionMessage(),
                            String.format(historyActionType.getDescriptionMessageFormat(), oldStatus, newStatus));
                    historyAuditService.getHistoryAuditEvents().add(history);
                    return;
                }
            }
        }
    }

    private boolean isStatusType(Object o) {
        return (o != null) && (o.getClass().equals(StatusType.class));
    }

    private History buildHistory(Ticket ticket, String action, String description) {
        return History
                .builder()
                .user(ticket.getOwner())
                .action(action)
                .ticket(ticket)
                .description(description)
                .build();
    }
}
