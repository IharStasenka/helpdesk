package com.training.istasenka.service.mailing;

import com.training.istasenka.model.mailing.MailingInfo;
import com.training.istasenka.model.user.User;
import com.training.istasenka.provider.link.LinkProvider;
import com.training.istasenka.provider.specification.mailrecepients.MailTemplateRecipientSpecificationProvider;
import com.training.istasenka.provider.specification.mailrecepients.MailTemplateRecipientSpecificationProviders;
import com.training.istasenka.repository.user.UserRepository;
import com.training.istasenka.util.MailTemplateType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final MailTemplateRecipientSpecificationProviders recipientSpecificationProviders;
    private final UserRepository userRepository;
    private final LinkProvider linkProvider;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;
    private static final String EVENTS = "mailingEvents";


    @Override
    public void sendEmails(List<SimpleMailMessage> mailDatas) {
        mailDatas.forEach(mailSender::send);
    }

    @Override
    public List<MailingInfo> getMailingAuditEvents() {
        if (!TransactionSynchronizationManager.hasResource(EVENTS)) {
            TransactionSynchronizationManager.bindResource(EVENTS, new ArrayList<List<MailingInfo>>());
        }
        return (List<MailingInfo>) TransactionSynchronizationManager.getResource(EVENTS);
    }

    @Override
    @Transactional
    public List<SimpleMailMessage> buildMailDatas(MailingInfo mailingInfo) {
        var ticketId = mailingInfo.getTicket().getId();
        var mailTemplate = mailingInfo.getMailTemplateType();
        var recipientSpecificationProvider = getMailTemplateRecipientSpecificationProvider(mailTemplate);
        var recipients = userRepository
                .findAll(recipientSpecificationProvider.getMailRecipientSpecificationByTicketId(ticketId));
        return recipients.stream().map(user -> buildMailData(mailTemplate, ticketId, user)).collect(Collectors.toList());
    }

    private MailTemplateRecipientSpecificationProvider getMailTemplateRecipientSpecificationProvider(MailTemplateType mailTemplate) {
        return recipientSpecificationProviders
                .getMailTemplateRecipientSpecificationProvider()
                .get(mailTemplate);
    }

    private SimpleMailMessage buildMailData(MailTemplateType mailTemplate, Long ticketId, User user) {
        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(mailTemplate.getMailSubject());
        mailMessage.setText(mailTemplate.getMailBody(user, getLink(mailTemplate, ticketId)));
        return mailMessage;
    }

    private Link getLink(MailTemplateType mailTemplate, Long ticketId) {
        if(mailTemplate.equals(MailTemplateType.TEMPLATE_7)) {
            return linkProvider.getTicketLinkForFeedback(ticketId);
        } else {
            return linkProvider.getTicketLink(ticketId);
        }
    }
}
