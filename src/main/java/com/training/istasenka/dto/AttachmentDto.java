package com.training.istasenka.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.ticket.View;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "attachments", itemRelation = "attachment")
public class AttachmentDto extends RepresentationModel<AttachmentDto> {
    @NumberFormat
    @JsonView({View.DisabledFields.class})
    private Long id;
    @JsonView({View.DisabledFields.class})
    private File file;
    @JsonView({View.FullTicket.class})
    private String name;
}
