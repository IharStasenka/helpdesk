package com.training.istasenka.assembler;

import com.training.istasenka.controller.AttachmentController;
import com.training.istasenka.converter.attachment.AttachmentConverter;
import com.training.istasenka.dto.AttachmentDto;
import com.training.istasenka.model.attachment.Attachment;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AttachmentDtoAssembler extends RepresentationModelAssemblerSupport<Attachment, AttachmentDto> {

    private static final String DOWNLOADS = "downloads";
    private final AttachmentConverter attachmentConverter;

    public AttachmentDtoAssembler(AttachmentConverter attachmentConverter) {
        super(AttachmentController.class, AttachmentDto.class);
        this.attachmentConverter = attachmentConverter;
    }

    @Override
    @NonNull
    public AttachmentDto toModel(@NonNull Attachment entity) {
        var attachmentDto = attachmentConverter.convertFromAttachment(entity);
        attachmentDto.add(
                linkTo(
                        methodOn(AttachmentController.class).getById(entity.getId(), entity.getTicket().getId()))
                        .withSelfRel());
        attachmentDto.add(
                linkTo(
                        methodOn(AttachmentController.class).getById(entity.getId(), entity.getTicket().getId()))
                        .slash(DOWNLOADS)
                        .withRel(DOWNLOADS));
        return attachmentDto;
    }

    public List<AttachmentDto> toModelList(List<Attachment> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
