package com.training.istasenka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.istasenka.dto.user.UserDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "histories", itemRelation = "history")
public class HistoryDto extends RepresentationModel<HistoryDto> {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @NumberFormat
    private Long id;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    private UserDto user;

    private String action;

}
