package com.training.istasenka.converter.history;

import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.HistoryDto;
import com.training.istasenka.model.history.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserConverter.class})
public interface HistoryConverter {

    HistoryConverter INSTANCE = Mappers.getMapper(HistoryConverter.class);


    HistoryDto convertFromHistory(History history);

    List<HistoryDto> convertFromHistorySet(List<History> histories);

}
