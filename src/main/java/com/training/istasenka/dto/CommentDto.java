package com.training.istasenka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.ticket.View;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.validator.textfield.TextFieldMatch;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "comments", itemRelation = "comment")
public class CommentDto extends RepresentationModel<CommentDto> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NumberFormat
    private Long id;

    @Size(max = 500)
    @TextFieldMatch
    @NotBlank
    @JsonView(View.FullTicket.class)
    private String text;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto user;

}
