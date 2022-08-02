package com.training.istasenka.provider.specification.mailrecepients;

import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.specification.mailrecipients.TemplateSevenSpecification;
import com.training.istasenka.util.MailTemplateType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MailTemplateSevenProvider implements MailTemplateRecipientSpecificationProvider {
    @Override
    public MailTemplateType getMailTemplateType() {
        return MailTemplateType.TEMPLATE_7;
    }

    @Override
    public Specification<User> getMailRecipientSpecificationByTicketId(Long ticketId) {
        return new TemplateSevenSpecification(ticketId);
    }
}
