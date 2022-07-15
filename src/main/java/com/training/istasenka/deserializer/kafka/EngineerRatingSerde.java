package com.training.istasenka.deserializer.kafka;

import com.training.istasenka.dto.user.EngineerRatingDto;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class EngineerRatingSerde extends Serdes.WrapperSerde<EngineerRatingDto>{
    public EngineerRatingSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(EngineerRatingDto.class));
    }

    public static EngineerRatingSerde getInstance() {
        return new EngineerRatingSerde();
    }
}
