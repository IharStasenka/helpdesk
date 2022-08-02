package com.training.istasenka.dto.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.istasenka.dto.user.UserDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "feedbacks", itemRelation = "feedback")
public class FeedbackDto extends RepresentationModel<FeedbackDto> {

    @NumberFormat
    private Long id;

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    @Max(value = 5, message = "Rate can't be greater than 5")
    @Min(value = 1, message = "Rate can't be less than 1")
    private Integer rate;
    private String text;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto user;
}
