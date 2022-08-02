package com.training.istasenka.provider.specification.mailrecepients;

import com.training.istasenka.util.MailTemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MailTemplateRecipientSpecificationProviders {

    @Autowired
    private List<MailTemplateRecipientSpecificationProvider> mailTemplateRecipientSpecificationProviderList;

    public Map<MailTemplateType, MailTemplateRecipientSpecificationProvider> getMailTemplateRecipientSpecificationProvider() {
        return mailTemplateRecipientSpecificationProviderList
                .stream()
                .collect(Collectors.toMap(MailTemplateRecipientSpecificationProvider::getMailTemplateType, Function.identity()));
    }
}
