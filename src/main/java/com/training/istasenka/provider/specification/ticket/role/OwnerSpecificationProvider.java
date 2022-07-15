package com.training.istasenka.provider.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.repository.specification.ticket.role.OwnerTicketSpecification;
import com.training.istasenka.util.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OwnerSpecificationProvider implements RoleSpecificationProvider {
    @Override
    public Specification<Ticket> getSpecificationForFindAllTickets(String username, Boolean myTicketFilterStatus) {
        return new OwnerTicketSpecification(username, myTicketFilterStatus);
    }

    @Override
    public UserRole getUserRole() {
        return UserRole.EMPLOYEE;
    }
}
