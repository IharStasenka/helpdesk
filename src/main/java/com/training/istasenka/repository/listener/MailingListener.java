package com.training.istasenka.repository.listener;

import com.training.istasenka.model.feedback.Feedback;
import com.training.istasenka.model.mailing.MailingInput;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.service.mailing.MailingService;
import com.training.istasenka.util.MailTemplateType;
import com.training.istasenka.util.StatusType;
import lombok.AllArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Component
@AllArgsConstructor
public class MailingListener implements PreInsertEventListener, PreUpdateEventListener {

    private final EntityManagerFactory entityManagerFactory;
    private final MailingService mailingService;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(this);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        var eventEntityClass = event.getEntity().getClass();
        if (eventEntityClass.equals(Feedback.class)) {
            var feedback = (Feedback) event.getEntity();
            addMailingEvent(feedback.getTicket(), MailTemplateType.TEMPLATE_7);
        }
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        if (preUpdateEvent.getEntity().getClass().equals(Ticket.class)) {
            var ticket = (Ticket) preUpdateEvent.getEntity();
            var oldState = preUpdateEvent.getOldState();
            for (Object o : oldState) {
                if (isStatusType(o)) {
                    var oldStatus = (StatusType) o;
                    var newStatus = ticket.getStatus();
                    if (!newStatus.equals(oldStatus)) {
                        Optional<MailTemplateType> mailTemplate
                                = MailTemplateType.getTemplateByStatuses(newStatus, oldStatus);
                        mailTemplate
                                .ifPresent(mailTemplateType -> addMailingEvent(ticket, mailTemplateType));
                    }
                }
            }

        }
        return false;
    }

    private void addMailingEvent(Ticket ticket, MailTemplateType mailTemplate) {
        mailingService.getMailingAuditEvents().add(
                MailingInput
                        .builder()
                        .ticket(ticket)
                        .mailTemplateType(mailTemplate)
                        .build());
    }

    private boolean isStatusType(Object o) {
        return (o != null) && (o.getClass().equals(StatusType.class));
    }
}
