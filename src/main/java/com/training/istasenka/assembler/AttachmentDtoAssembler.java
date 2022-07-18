package com.training.istasenka.assembler;

import com.training.istasenka.controller.AttachmentController;
import com.training.istasenka.converter.attachment.AttachmentConverter;
import com.training.istasenka.dto.AttachmentDto;
import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.provider.link.LinkProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttachmentDtoAssembler extends RepresentationModelAssemblerSupport<Attachment, AttachmentDto> {

    private final AttachmentConverter attachmentConverter;
    private final LinkProvider linkProvider;

    public AttachmentDtoAssembler(AttachmentConverter attachmentConverter, LinkProvider linkProvider) {
        super(AttachmentController.class, AttachmentDto.class);
        this.attachmentConverter = attachmentConverter;
        this.linkProvider = linkProvider;
    }

    @Override
    @NonNull
    public AttachmentDto toModel(@NonNull Attachment entity) {
        var ticketId = entity.getTicket().getId();
        var attachmentId =entity.getId();
        var attachmentDto = attachmentConverter.convertFromAttachment(entity);
        attachmentDto.add(linkProvider.getAttachmentLink(ticketId, attachmentId));
        attachmentDto.add(linkProvider.getAttachmentDownloadLink(ticketId, attachmentId));
        return attachmentDto;
    }

    public List<AttachmentDto> toModelList(List<Attachment> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
