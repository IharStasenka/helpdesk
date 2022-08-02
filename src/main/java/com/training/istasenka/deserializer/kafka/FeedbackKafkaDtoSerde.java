package com.training.istasenka.deserializer.kafka;

import com.training.istasenka.dto.feedback.FeedbackKafkaDto;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class FeedbackKafkaDtoSerde extends Serdes.WrapperSerde<FeedbackKafkaDto> {

    public FeedbackKafkaDtoSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(FeedbackKafkaDto.class, false));
    }

    public static FeedbackKafkaDtoSerde getInstance() {
        return new FeedbackKafkaDtoSerde();
    }
}
