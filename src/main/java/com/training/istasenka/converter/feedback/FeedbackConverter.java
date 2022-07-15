package com.training.istasenka.converter.feedback;

import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.feedback.FeedbackDto;
import com.training.istasenka.model.feedback.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserConverter.class})
public interface FeedbackConverter {

    FeedbackConverter INSTANCE = Mappers.getMapper(FeedbackConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "user", ignore = true)
    Feedback convertFeedbackDtoToFeedBack(FeedbackDto feedbackDto);

    FeedbackDto convertFeedbackToFeedbackDto(Feedback feedback);
}
