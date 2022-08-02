package com.training.istasenka.service.attachment;

import com.training.istasenka.model.attachment.Attachment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    List<Long> createAttachments(Long ticketId, List<Attachment> attachments) throws IOException;

    void deleteAttachment(Long ticketId, Long attachmentId);

    Attachment getAttachment(Long ticketId, Long attachmentId);

    List<Attachment> getAllAttachmentsByTicketId(Long ticketId);

    Resource downloadAttachment(Long ticketId, Long attachmentId);
}
