package com.training.istasenka.service.history;

import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.exception.TicketNotFoundException;
import com.training.istasenka.model.history.History;
import com.training.istasenka.repository.history.HistoryRepository;
import com.training.istasenka.provider.specification.history.HistorySpecificationProvider;
import com.training.istasenka.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final TicketService ticketService;
    private final HistorySpecificationProvider historySpecificationProvider;


    @Override
    public History getHistoryById(Long ticketId, Long historyId) {
        ticketService.validateTicketResourceById(ticketId);
        var history = historyRepository.findById(historyId).orElseThrow(() -> new TicketNotFoundException(""));
        if (!history.getTicket().getId().equals(historyId)) {
            throw new CustomIllegalArgumentException("TicketId is not consistent with historyId");
        }
        return history;
    }

    @Override
    public Page<History> getHistoryPage(Long ticketId, Pageable pageable) {
        var specification = historySpecificationProvider.getHistoryBySpecificationProvider(ticketId);
        ticketService.validateTicketResourceById(ticketId);
        return historyRepository.findAll(specification, pageable);
    }
}
