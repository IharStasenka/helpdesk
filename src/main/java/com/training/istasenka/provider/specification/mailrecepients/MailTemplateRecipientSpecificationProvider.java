package com.training.istasenka.provider.specification.mailrecepients;

import com.training.istasenka.model.user.User;
import com.training.istasenka.util.MailTemplateType;
import org.springframework.data.jpa.domain.Specification;

public interface MailTemplateRecipientSpecificationProvider {

    MailTemplateType getMailTemplateType();

    Specification<User> getMailRecipientSpecificationByTicketId(Long ticketId);

}
