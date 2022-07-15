package com.training.istasenka.service.history;

import com.training.istasenka.model.history.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {

    History getHistoryById(Long ticketId, Long historyId);

    Page<History> getHistoryPage(Long ticketId, Pageable pageable);
}
