package com.training.istasenka.service.history.audit;

import com.training.istasenka.model.history.History;
import com.training.istasenka.repository.history.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryAuditServiceImpl implements HistoryAuditService{

    private final HistoryRepository historyRepository;

    private static final String EVENTS = "events";

    public List<History> getHistoryAuditEvents() {
        if (!TransactionSynchronizationManager.hasResource(EVENTS)) {
            TransactionSynchronizationManager.bindResource(EVENTS, new ArrayList<History>());
        }
        return (List<History>) TransactionSynchronizationManager.getResource(EVENTS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(History history) {
        historyRepository.save(history);
    }
}
