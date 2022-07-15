package com.training.istasenka.repository.ticket;

import com.training.istasenka.model.ticket.Ticket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    @Override
    @NonNull
    @EntityGraph(value = "Ticket.comments")
    Optional<Ticket> findOne(Specification specification);

}
