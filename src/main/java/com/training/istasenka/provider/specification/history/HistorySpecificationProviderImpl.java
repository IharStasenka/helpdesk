package com.training.istasenka.provider.specification.history;

import com.training.istasenka.repository.specification.history.HistoriesByTicketIdSpecification;
import org.springframework.stereotype.Component;

@Component
public class HistorySpecificationProviderImpl implements HistorySpecificationProvider {

    @Override
    public HistoriesByTicketIdSpecification getHistoryBySpecificationProvider(Long ticketId) {
        return new HistoriesByTicketIdSpecification(ticketId);
    }
}
