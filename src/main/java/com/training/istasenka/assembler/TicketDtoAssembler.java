package com.training.istasenka.assembler;

import com.training.istasenka.controller.TicketController;
import com.training.istasenka.converter.ticket.TicketConverter;
import com.training.istasenka.dto.ticket.TicketDto;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.provider.link.LinkProvider;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TicketDtoAssembler extends RepresentationModelAssemblerSupport<Ticket, TicketDto> {

    private final TicketConverter ticketConverter;
    private final AttachmentDtoAssembler attachmentDtoAssembler;
    private final LinkProvider linkProvider;

    public TicketDtoAssembler(TicketConverter ticketConverter,
                              AttachmentDtoAssembler attachmentDtoAssembler,
                              LinkProvider linkProvider) {
        super(TicketController.class, TicketDto.class);
        this.ticketConverter = ticketConverter;
        this.attachmentDtoAssembler = attachmentDtoAssembler;
        this.linkProvider = linkProvider;
    }

    @Override
    @NonNull
    public CollectionModel<TicketDto> toCollectionModel(Iterable<? extends Ticket> entities) {
        var tickets = new ArrayList<TicketDto>();
        entities.forEach(ticket -> {
            var ticketDto = ticketConverter.convertFromTicketForFrontTable(ticket);
            ticketDto.add(linkProvider.getTicketLink(ticket.getId()));
            tickets.add(ticketDto);
        });
        return CollectionModel.of(tickets);
    }

    @Override
    @NonNull
    public TicketDto toModel(@NonNull Ticket entity) {
        var ticketDto = ticketConverter.convertFromTicketForFrontTable(entity);
        ticketDto.add(linkProvider.getTicketLink(entity.getId()));
        return ticketDto;
    }

    public TicketDto toFullModel(Ticket entity) {
        var ticketDto = ticketConverter.convertFromTicket(entity);
        var ticketId = entity.getId();
        ticketDto.setAttachments(attachmentDtoAssembler.toModelList(entity.getAttachments()));
        ticketDto.add(linkProvider.getTicketLink(ticketId));
        ticketDto.add(linkProvider.getDefaultCommentPageLink(ticketId));
        ticketDto.add(linkProvider.getDefaultHistoryPageLink(ticketId));
        ticketDto.add(linkProvider.getAttachmentsLink(ticketId));
        return ticketDto;
    }

}
