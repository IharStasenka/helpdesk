package com.training.istasenka.model.mailing;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.MailTemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MailingInfo{
    private Ticket ticket;
    private MailTemplateType mailTemplateType;
}
