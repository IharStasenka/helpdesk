package com.training.istasenka.assembler;

import com.training.istasenka.controller.TicketController;
import com.training.istasenka.converter.ticket.TicketConverter;
import com.training.istasenka.dto.ticket.TicketDto;
import com.training.istasenka.model.ticket.Ticket;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.training.istasenka.model.ticket.Ticket_.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TicketDtoAssembler extends RepresentationModelAssemblerSupport<Ticket, TicketDto> {

    private final TicketConverter ticketConverter;
    private final AttachmentDtoAssembler attachmentDtoAssembler;

    public TicketDtoAssembler(TicketConverter ticketConverter, AttachmentDtoAssembler attachmentDtoAssembler) {
        super(TicketController.class, TicketDto.class);
        this.ticketConverter = ticketConverter;
        this.attachmentDtoAssembler = attachmentDtoAssembler;
    }

    @Override
    @NonNull
    public CollectionModel<TicketDto> toCollectionModel(Iterable<? extends Ticket> entities) {
        var tickets = new ArrayList<TicketDto>();
        entities.forEach(ticket -> {
            var ticketDto = ticketConverter.convertFromTicketForFrontTable(ticket);
            ticketDto.add(linkTo(getControllerClass()).slash(ticket.getId()).withSelfRel());
            tickets.add(ticketDto);
        });
        return CollectionModel.of(tickets);
    }

    @Override
    @NonNull
    public TicketDto toModel(@NonNull Ticket entity) {
        var ticketDto = ticketConverter.convertFromTicketForFrontTable(entity);
        ticketDto.add(linkTo(getControllerClass()).slash(entity.getId()).withSelfRel());
        return ticketDto;
    }

    public TicketDto toFullModel(Ticket entity) {
        var ticketDto = ticketConverter.convertFromTicket(entity);
        ticketDto.add(linkTo(getControllerClass()).slash(entity.getId()).withSelfRel());
        ticketDto.add(linkTo(getControllerClass()).slash(entity.getId()).slash(COMMENTS).withRel(COMMENTS));
        ticketDto.add(linkTo(getControllerClass()).slash(entity.getId()).slash(ATTACHMENTS).withRel(ATTACHMENTS));
        ticketDto.add(linkTo(getControllerClass()).slash(entity.getId()).slash(HISTORIES).withRel(HISTORIES));
        ticketDto.setAttachments(attachmentDtoAssembler.toModelList(entity.getAttachments()));
        return ticketDto;
    }

}
