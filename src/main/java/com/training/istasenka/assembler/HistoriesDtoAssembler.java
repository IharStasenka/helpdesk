package com.training.istasenka.assembler;

import com.training.istasenka.controller.HistoryController;
import com.training.istasenka.converter.history.HistoryConverter;
import com.training.istasenka.dto.HistoryDto;
import com.training.istasenka.model.history.History;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistoriesDtoAssembler extends RepresentationModelAssemblerSupport<History, HistoryDto> {

    private final HistoryConverter historyConverter;

    public HistoriesDtoAssembler(HistoryConverter historyConverter) {
        super(HistoryController.class, HistoryDto.class);
        this.historyConverter = historyConverter;
    }

    @Override
    @NonNull
    public HistoryDto toModel(@NonNull History entity) {
        var historyDto = historyConverter.convertFromHistory(entity);
        return historyDto
                .add(linkTo(methodOn(HistoryController.class).getById(entity.getId(), entity.getTicket().getId())).withSelfRel());
    }

    public List<HistoryDto> toModelList(List<History> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
