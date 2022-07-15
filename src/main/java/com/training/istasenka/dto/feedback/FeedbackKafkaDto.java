package com.training.istasenka.dto.feedback;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize
@JsonSerialize
public class FeedbackKafkaDto extends RepresentationModel<FeedbackKafkaDto> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    @Max(value = 5, message = "Rate can't be greater than 5")
    @Min(value = 1, message = "Rate can't be less than 1")
    private Integer rate;

    private String text;

    private String ownerEmail;

    private String assigneeEmail;

    private Long ticketId;

}
