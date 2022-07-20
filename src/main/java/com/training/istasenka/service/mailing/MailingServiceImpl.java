package com.training.istasenka.service.mailing;

import com.training.istasenka.model.mailing.MailData;
import com.training.istasenka.model.mailing.MailingInput;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;
import com.training.istasenka.provider.link.LinkProvider;
import com.training.istasenka.provider.specification.mailrecepients.MailTemplateRecipientSpecificationProvider;
import com.training.istasenka.repository.user.UserRepository;
import com.training.istasenka.util.MailTemplateType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final Map<MailTemplateType, MailTemplateRecipientSpecificationProvider> recipientSpecificationProviders;
    private final UserRepository userRepository;
    private final LinkProvider linkProvider;
    private static final String EVENTS = "mailingEvents";

    @Override
    public void sendEmails(List<MailData> mailDatas) {
        mailDatas.forEach(System.out::println);
    }

    @Override
    public List<MailingInput> getMailingAuditEvents() {
        if (!TransactionSynchronizationManager.hasResource(EVENTS)) {
            TransactionSynchronizationManager.bindResource(EVENTS, new ArrayList<List<MailData>>());
        }
        return (List<MailingInput>) TransactionSynchronizationManager.getResource(EVENTS);
    }

    @Override
    @Transactional
    public List<MailData> buildMailDatas(Ticket ticket, MailTemplateType mailTemplate) {
        var ticketId = ticket.getId();
        var recipientSpecificationProvider = recipientSpecificationProviders
                .get(mailTemplate);
        var recipients = userRepository
                .findAll(recipientSpecificationProvider.getMailRecipientSpecificationByTicketId(ticketId));
        return recipients.stream().map(user -> buildMailData(mailTemplate, ticketId, user)).collect(Collectors.toList());
    }

    private MailData buildMailData(MailTemplateType mailTemplate, Long ticketId, User user) {
        return MailData
                .builder()
                .subject(mailTemplate.getMailSubject())
                .body(mailTemplate.getMailBody(user, linkProvider.getTicketLink(ticketId)))
                .email(user.getEmail()).build();
    }
}
