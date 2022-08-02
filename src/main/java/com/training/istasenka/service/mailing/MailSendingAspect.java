package com.training.istasenka.service.mailing;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@AllArgsConstructor
@Component
public class MailSendingAspect implements TransactionSynchronization {

    private final MailingService mailingService;

    @Before("execution(* *.*(..)) && @annotation(com.training.istasenka.annotation.MailAudit)")
    public void registerTransactionSynchronizationManager(JoinPoint jp) {
        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void afterCommit() {
        mailingService.getMailingAuditEvents();
        if (!TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                && TransactionSynchronizationManager.isActualTransactionActive()) {
            var mailingAuditEvents = mailingService.getMailingAuditEvents();
            mailingAuditEvents.stream().map(mailingService::buildMailDatas).forEach(mailingService::sendEmails);
        }
    }
}
