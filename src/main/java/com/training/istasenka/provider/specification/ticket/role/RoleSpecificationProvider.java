package com.training.istasenka.provider.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.UserRole;
import org.springframework.data.jpa.domain.Specification;

public interface RoleSpecificationProvider {

    Specification<Ticket> getSpecificationForFindAllTickets(String username, Boolean myTicketFilterStatus);

    UserRole getUserRole();

}
