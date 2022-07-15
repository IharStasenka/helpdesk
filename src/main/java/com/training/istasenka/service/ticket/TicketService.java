package com.training.istasenka.service.ticket;

import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.TicketActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    Page<Ticket> getAllTickets(Pageable pagination, String filterBy, Boolean myTicketFilterStatus);

    Boolean validateTicketResourceById(Long id);

    void validateTicketResourceByIdByEmail(Long ticketId, String email);

    Ticket getTicketById(Long ticketId);

    Ticket getTicketByIdByEmail(Long ticketId, String email);

    Long saveTicket(Ticket ticket, List<Attachment> multipartFiles);

    void updateTicket(Ticket ticket, List<Attachment> multipartFiles, Long id);

    void deleteTicket(Long id);

    void updateTicketStatus(Long id, TicketActionType action);
}
