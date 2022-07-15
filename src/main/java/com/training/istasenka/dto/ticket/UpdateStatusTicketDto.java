package com.training.istasenka.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.istasenka.util.TicketActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonProperty.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateStatusTicketDto {

    @NotNull
    @JsonProperty(access = Access.READ_WRITE)
    private TicketActionType action;
}
