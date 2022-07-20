package com.training.istasenka.service.mailing;

import com.training.istasenka.model.mailing.MailData;
import com.training.istasenka.model.mailing.MailingInput;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.MailTemplateType;

import java.util.List;

public interface MailingService {

    List<MailingInput> getMailingAuditEvents();

    void sendEmails(List<MailData> mailDatas);

    List<MailData> buildMailDatas(Ticket ticket, MailTemplateType mailTemplateType);
}
