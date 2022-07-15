package com.training.istasenka.converter.ticket;

import com.training.istasenka.annotation.DoIgnore;
import com.training.istasenka.converter.attachment.AttachmentConverter;
import com.training.istasenka.converter.category.CategoryConverter;
import com.training.istasenka.converter.comment.CommentConverter;
import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.ticket.TicketDto;
import com.training.istasenka.model.ticket.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommentConverter.class, UserConverter.class, CategoryConverter.class, AttachmentConverter.class})
public interface TicketConverter {

    TicketConverter INSTANCE = Mappers.getMapper(TicketConverter.class);


    @Mapping(target = "description", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "comments", ignore = true)
    TicketDto convertFromTicketForFrontTable(Ticket ticket);

    @Mapping(target = "comments", ignore = true)
    @DoIgnore
    TicketDto convertFromTicket(Ticket ticket);


    List<TicketDto> convertFromTicketSet(List<Ticket> tickets);

    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Ticket convertFromTicketDto(TicketDto ticketDto);
}
