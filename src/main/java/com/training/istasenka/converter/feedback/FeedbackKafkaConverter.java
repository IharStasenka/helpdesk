package com.training.istasenka.converter.feedback;

import com.training.istasenka.dto.feedback.FeedbackKafkaDto;
import com.training.istasenka.model.feedback.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeedbackKafkaConverter {

    FeedbackKafkaConverter INSTANCE = Mappers.getMapper(FeedbackKafkaConverter.class);

    @Mapping(source = "ticket.id", target = "ticketId")
    @Mapping(source = "user.email", target = "ownerEmail")
    @Mapping(source = "ticket.assignee.email", target = "assigneeEmail")
    FeedbackKafkaDto convertFeedbackToFeedbackDto(Feedback feedback);

    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    Feedback convertFromFeedbackDtoToFeedback(FeedbackKafkaDto feedbackKafkaDto);

}
