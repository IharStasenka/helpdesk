package com.training.istasenka.provider.specification.mailrecepients;

import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.specification.mailrecipients.TemplateFourSpecification;
import com.training.istasenka.util.MailTemplateType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MailTemplateFourProvider implements MailTemplateRecipientSpecificationProvider{
    @Override
    public MailTemplateType getMailTemplateType() {
        return MailTemplateType.TEMPLATE_4;
    }

    @Override
    public Specification<User> getMailRecipientSpecificationByTicketId(Long ticketId) {
        return new TemplateFourSpecification(ticketId);
    }
}
