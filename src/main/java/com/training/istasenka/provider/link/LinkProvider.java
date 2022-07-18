package com.training.istasenka.provider.link;

import org.springframework.hateoas.Link;

public interface LinkProvider {

    Link getDefaultTicketPageLink();

    Link getTicketLink(Long ticketId);

    Link getDefaultCommentPageLink(Long ticketId);

    Link getCommentLink(Long ticketId, Long commentId);

    Link getAttachmentsLink(Long ticketId);

    Link getAttachmentDownloadLink(Long ticketId, Long attachmentId);

    Link getAttachmentLink(Long ticketId, Long attachmentId);

    Link getDefaultHistoryPageLink(Long ticketId);

    Link getHistoryLink(Long ticketId, Long historyId);

    Link getUserLink(String username);
}
