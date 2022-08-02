package com.training.istasenka.service.feedback;

import com.training.istasenka.deserializer.kafka.EngineerRatingSerde;
import com.training.istasenka.deserializer.kafka.FeedbackKafkaDtoSerde;
import com.training.istasenka.dto.user.EngineerRatingDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.training.istasenka.util.KafkaTopicType.ENGINEER_RATING_TOPIC;
import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;
import static org.apache.kafka.streams.Topology.AutoOffsetReset.EARLIEST;

@Component
@AllArgsConstructor
public class FeedbackKafka {

    private final FeedbackService feedbackService;

    @Autowired
    public void processKafkaStreamWithFeedback(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream(FEEDBACK_TOPIC.getTopicName(),
                        Consumed.with(Serdes.String(),
                                FeedbackKafkaDtoSerde.getInstance()).withOffsetResetPolicy(EARLIEST))
                .foreach(feedbackService::saveFeedbackFromKafkaTopic);
    }

    @Autowired
    public void aggregateEngineerMarks(StreamsBuilder streamsBuilder) {
        KTable<String, EngineerRatingDto> ratingTable = streamsBuilder
                .stream(FEEDBACK_TOPIC.getTopicName(), Consumed.with(Serdes.String(), FeedbackKafkaDtoSerde.getInstance()).withOffsetResetPolicy(EARLIEST))
                .groupBy((k, v) -> v.getAssigneeEmail())
                .aggregate(() -> new EngineerRatingDto(0L, BigDecimal.ZERO),
                        (k, v, agg) -> {
                            Long prevCount = agg.getNumberOfDoneTickets();
                            agg.setNumberOfDoneTickets(agg.getNumberOfDoneTickets() + 1);
                            agg.setAverageRating((
                                    agg.getAverageRating().multiply(BigDecimal.valueOf(prevCount)).add(BigDecimal.valueOf(v.getRate())))
                                    .divide(BigDecimal.valueOf(agg.getNumberOfDoneTickets()), 2, RoundingMode.HALF_DOWN));
                            return agg;
                        },
                        Materialized
                                .<String, EngineerRatingDto, KeyValueStore<Bytes, byte[]>>as("rating")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(EngineerRatingSerde.getInstance()));
        ratingTable.toStream().to(ENGINEER_RATING_TOPIC.getTopicName(), Produced.with(Serdes.String(), EngineerRatingSerde.getInstance()));
    }
}
