package com.training.istasenka.dto.user;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EngineerRatingDto {

    private Long numberOfDoneTickets;

    private BigDecimal averageRating;

}
