package com.training.istasenka.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.AttachmentDto;
import com.training.istasenka.dto.CategoryDto;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.dto.CommentDto;
import com.training.istasenka.dto.validationgroups.UpdateInfo;
import com.training.istasenka.util.StatusType;
import com.training.istasenka.util.UrgencyType;
import com.training.istasenka.validator.textfield.TextFieldMatch;
import com.training.istasenka.validator.ticketcreationparams.EnumFieldMatch;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access;


@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "tickets", itemRelation = "ticket")
public class TicketDto extends RepresentationModel<TicketDto> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Positive(
            message = "Id must be greater than 0",
            groups = {UpdateInfo.class})
    @NotNull(
            message = "Field with name id must not be null",
            groups = {UpdateInfo.class})
    @JsonView({View.ShortTicket.class})
    private Long id;

    @TextFieldMatch(message = "Incorrect name text format")
    @Size(max = 100)
    @NotBlank
    @JsonView({View.ShortTicket.class})
    private String name;

    @TextFieldMatch(message = "Incorrect description text format")
    @Size(max = 500)
    @JsonProperty
    @JsonView(View.FullTicket.class)
    private String description;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    @JsonView(View.FullTicket.class)
    private LocalDate createdOn;

    @Future(message = "Must contain a date that has not yet occurred")
    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    @JsonView({View.ShortTicket.class})
    private LocalDate desiredResolutionDate;

    @NotNull
    @EnumFieldMatch(enumClass = UrgencyType.class)
    @JsonView({View.ShortTicket.class})
    private UrgencyType urgency;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonView({View.FullTicket.class})
    private UserDto owner;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonView({View.FullTicket.class})
    private UserDto approver;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonView({View.FullTicket.class})
    private UserDto assignee;

    @NotNull
    @JsonView({View.ShortTicket.class})
    private StatusType status;

    @NotNull
    @Valid
    @JsonView({View.FullTicket.class})
    private CategoryDto category;

    @JsonView({View.FullTicket.class})
    private List<AttachmentDto> attachments;

    @JsonProperty(access = Access.WRITE_ONLY)
    private List<@Valid CommentDto> comments;
}
