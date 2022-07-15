package com.training.istasenka.service.attachment;

import com.training.istasenka.annotation.HistoryAudit;
import com.training.istasenka.exception.AttachmentNotFoundException;
import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.repository.attachment.AttachmentRepository;
import com.training.istasenka.provider.specification.attachment.AttachmentSpecificationProvider;
import com.training.istasenka.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final TicketService ticketService;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentSpecificationProvider attachmentSpecificationProvider;

    @Override
    @Transactional
    @HistoryAudit
    public List<Long> createAttachments(Long ticketId, List<Attachment> attachments) throws IOException {
        var ticket = ticketService.getTicketById(ticketId);
        attachments.forEach(attachment -> attachment.setTicket(ticket));
        return attachmentRepository.saveAll(attachments).stream().map(Attachment::getId).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @HistoryAudit
    public void deleteAttachment(Long ticketId, Long attachmentId) {
        getAttachment(ticketId, attachmentId);
        attachmentRepository.deleteById(attachmentId);
    }

    @Override
    @Transactional
    public Attachment getAttachment(Long ticketId, Long attachmentId) {
        ticketService.validateTicketResourceById(ticketId);
        return attachmentRepository
                .findById(attachmentId)
                .orElseThrow(() -> new AttachmentNotFoundException(String.format("There are no attachment with id % d", attachmentId)));
    }

    @Override
    public List<Attachment> getAllAttachmentsByTicketId(Long ticketId) {
        return attachmentRepository.findAll(attachmentSpecificationProvider.getAttachmentSpecification(ticketId));
    }

    @Override
    @Transactional
    public Resource downloadAttachment(Long ticketId, Long attachmentId) {
        var attachment = getAttachment(ticketId, attachmentId);

        return new ByteArrayResource(attachment.getFile());
    }
}
