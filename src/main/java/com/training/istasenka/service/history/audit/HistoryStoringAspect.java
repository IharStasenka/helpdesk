package com.training.istasenka.service.history.audit;

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
public class HistoryStoringAspect implements TransactionSynchronization {

    private final HistoryAuditService historyAuditService;

    @Before("execution(* *.*(..)) && @annotation(com.training.istasenka.annotation.HistoryAudit)")
    public void registerTransactionSynchronizationManager(JoinPoint jp) {
        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void afterCommit() {
        historyAuditService.getHistoryAuditEvents();
        if (!TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                && TransactionSynchronizationManager.isActualTransactionActive()) {
            historyAuditService.getHistoryAuditEvents().forEach(historyAuditService::save);
        }
    }
}
