package com.training.istasenka.service.mailing;

import com.training.istasenka.model.mailing.MailingInfo;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.MailTemplateType;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface MailingService {

    List<MailingInfo> getMailingAuditEvents();

    void sendEmails(List<SimpleMailMessage> mailDatas);

    List<SimpleMailMessage> buildMailDatas(MailingInfo mailingInfo);
}
