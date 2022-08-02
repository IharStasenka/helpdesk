package com.training.istasenka.service.history.audit;

import com.training.istasenka.model.history.History;

import java.util.List;

public interface HistoryAuditService {

    List<History> getHistoryAuditEvents();

    void save(History history);

}
