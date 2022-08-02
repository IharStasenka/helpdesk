package com.training.istasenka.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.ticket.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @JsonView({View.DisabledFields.class})
    private Long id;
    @JsonView({View.FullTicket.class})
    private String name;
}
