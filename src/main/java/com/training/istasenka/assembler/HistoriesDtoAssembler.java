package com.training.istasenka.assembler;

import com.training.istasenka.controller.HistoryController;
import com.training.istasenka.converter.history.HistoryConverter;
import com.training.istasenka.dto.HistoryDto;
import com.training.istasenka.model.history.History;
import com.training.istasenka.provider.link.LinkProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoriesDtoAssembler extends RepresentationModelAssemblerSupport<History, HistoryDto> {

    private final HistoryConverter historyConverter;
    private final LinkProvider linkProvider;

    public HistoriesDtoAssembler(HistoryConverter historyConverter, LinkProvider linkProvider) {
        super(HistoryController.class, HistoryDto.class);
        this.historyConverter = historyConverter;
        this.linkProvider = linkProvider;
    }

    @Override
    @NonNull
    public HistoryDto toModel(@NonNull History entity) {
        var historyDto = historyConverter.convertFromHistory(entity);
        return historyDto.add(linkProvider.getHistoryLink(entity.getTicket().getId(), entity.getId()));
    }

    public List<HistoryDto> toModelList(List<History> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
