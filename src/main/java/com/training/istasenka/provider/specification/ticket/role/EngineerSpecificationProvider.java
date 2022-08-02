package com.training.istasenka.provider.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.repository.specification.ticket.role.EngineerTicketsSpecification;
import com.training.istasenka.util.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EngineerSpecificationProvider implements RoleSpecificationProvider {

    @Override
    public Specification<Ticket> getSpecificationForFindAllTickets(String username, Boolean myTicketFilterStatus) {
        return new EngineerTicketsSpecification(username, myTicketFilterStatus);
    }

    @Override
    public UserRole getUserRole() {
        return UserRole.ENGINEER;
    }
}
