package com.training.istasenka.provider.link;

import com.training.istasenka.controller.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static com.training.istasenka.model.ticket.Ticket_.*;
import static com.training.istasenka.model.ticket.Ticket_.ATTACHMENTS;
import static com.training.istasenka.repository.pagiablecomponent.PageableEntity.*;
import static com.training.istasenka.repository.pagiablecomponent.PageableEntity.TICKET;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkProviderImpl implements LinkProvider {

    private static final String DOWNLOADS = "downloads";
    private static final String TICKETS = "tickets";

    @Override
    public Link getDefaultTicketPageLink() {
        return linkTo(methodOn(TicketController.class)
                .getAllUserTickets(
                        TICKET.getPage(),
                        TICKET.getSize(),
                        TICKET.getFieldName(),
                        TICKET.getDirection(),
                        Strings.EMPTY,
                        false))
                .withRel(TICKETS);
    }

    @Override
    public Link getTicketLink(Long ticketId) {
        return linkTo(methodOn(TicketController.class).getTicketById(ticketId)).withSelfRel();
    }

    @Override
    public Link getDefaultCommentPageLink(Long ticketId) {
        return linkTo(methodOn(CommentController.class)
                .getAllByTicketId(
                        ticketId,
                        COMMENT.getPage(),
                        COMMENT.getSize(),
                        COMMENT.getFieldName(),
                        COMMENT.getDirection()))
                .withRel(COMMENTS);
    }

    @Override
    public Link getCommentLink(Long ticketId, Long commentId) {
        return linkTo(methodOn(CommentController.class).getById(commentId, ticketId)).withSelfRel();
    }

    @Override
    public Link getAttachmentsLink(Long ticketId) {
        return linkTo(methodOn(AttachmentController.class).getAllByTicketId(ticketId)).withRel(ATTACHMENTS);
    }

    @Override
    public Link getAttachmentDownloadLink(Long ticketId, Long attachmentId) {
        return linkTo(
                methodOn(AttachmentController.class).getById(attachmentId, ticketId))
                .slash(DOWNLOADS)
                .withRel(DOWNLOADS);
    }

    @Override
    public Link getAttachmentLink(Long ticketId, Long attachmentId) {
        return linkTo(methodOn(AttachmentController.class).getById(attachmentId, ticketId)).withSelfRel();
    }

    @Override
    public Link getDefaultHistoryPageLink(Long ticketId) {
        return linkTo(methodOn(HistoryController.class)
                .getAllByTicketId(
                        ticketId,
                        HISTORY.getPage(),
                        HISTORY.getSize(),
                        HISTORY.getFieldName(),
                        HISTORY.getDirection()))
                .withRel(HISTORIES);
    }

    @Override
    public Link getHistoryLink(Long ticketId, Long historyId) {
        return linkTo(methodOn(HistoryController.class).getById(historyId, ticketId)).withSelfRel();
    }

    @Override
    public Link getUserLink(String username) {
        return linkTo(methodOn(UserController.class).getByName(username)).withSelfRel();
    }
}
