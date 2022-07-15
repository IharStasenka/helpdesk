package com.training.istasenka.provider.specification.history;

import com.training.istasenka.repository.specification.history.HistoriesByTicketIdSpecification;

public interface HistorySpecificationProvider {

    HistoriesByTicketIdSpecification getHistoryBySpecificationProvider(Long ticketId);

}
