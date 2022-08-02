package com.training.istasenka.converter.attachment;

import com.training.istasenka.dto.AttachmentDto;
import com.training.istasenka.exception.MultipartFileProcessingException;
import com.training.istasenka.model.attachment.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper
public interface AttachmentConverter {

    AttachmentConverter INSTANCE = Mappers.getMapper(AttachmentConverter.class);

    List<Attachment> convertFromAttachmentDtoList(List<MultipartFile> attachmentList) throws IOException;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "file", ignore = true)
    AttachmentDto convertFromAttachment(Attachment attachment);

    List<AttachmentDto> convertFromAttachmentList(List<Attachment> attachments);

    default Attachment convertFromAttachmentDto(MultipartFile attachmentDto) {
        try {
            return Attachment
                    .builder()
                    .file(attachmentDto.getBytes())
                    .name(attachmentDto.getOriginalFilename())
                    .build();
        } catch (IOException exception) {
            throw new MultipartFileProcessingException(exception.getMessage());
        }
    }
}
