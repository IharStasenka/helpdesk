package com.training.istasenka.service.feedback;

import com.training.istasenka.converter.feedback.FeedbackKafkaConverter;
import com.training.istasenka.deserializer.kafka.EngineerRatingSerde;
import com.training.istasenka.deserializer.kafka.FeedbackKafkaDtoSerde;
import com.training.istasenka.dto.feedback.FeedbackKafkaDto;
import com.training.istasenka.dto.user.EngineerRatingDto;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.exception.FeedbackNotFoundException;
import com.training.istasenka.model.feedback.Feedback;
import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.feedback.FeedbackRepository;
import com.training.istasenka.service.ticket.TicketService;
import com.training.istasenka.service.user.UserService;
import com.training.istasenka.util.StatusType;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.training.istasenka.util.KafkaTopicType.ENGINEER_RATING_TOPIC;
import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;
import static org.apache.kafka.streams.Topology.AutoOffsetReset.EARLIEST;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final TicketService ticketService;
    private final UserService userService;
    private final FeedbackRepository feedbackRepository;
    private final KafkaTemplate<String, FeedbackKafkaDto> kafkaTemplate;
    private final FeedbackKafkaConverter feedbackKafkaConverter;

    @Override
    @Transactional
    public Long saveFeedback(Feedback feedback, Long ticketId) {
        var ticket = ticketService.getTicketById(ticketId);
        if (!ticket.getStatus().equals(StatusType.DONE)) {
            throw new CustomIllegalArgumentException("It's possible to provide feedback only in done status");
        }
        if (!getUserFromSecurityContext().getEmail().equals(ticket.getOwner().getEmail())) {
            throw new AccessDeniedException("Feedback can be provided only by ticket creator");
        }
        feedback.setUser(ticket.getOwner());
        feedback.setTicket(ticket);
        var feedbackKafkaDto = feedbackKafkaConverter.convertFeedbackToFeedbackDto(feedback);
        ListenableFuture<SendResult<String, FeedbackKafkaDto>> futureTopic = kafkaTemplate.send(FEEDBACK_TOPIC.getTopicName(), feedbackKafkaDto);
        processCallBackForFeedbackKafka(feedbackKafkaDto, futureTopic);
        return 1L;
    }

    @Transactional
    @Autowired
    public void processKafkaStreamWithFeedback(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream(FEEDBACK_TOPIC.getTopicName(),
                        Consumed.with(Serdes.String(),
                        FeedbackKafkaDtoSerde.getInstance()).withOffsetResetPolicy(EARLIEST))
                .foreach(this::saveFeedbackFromKafkaTopic);
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


    @Override
    @Transactional
    public Feedback getFeedback(Long ticketId, Long feedbackId) {
        ticketService.validateTicketResourceById(ticketId);
        return feedbackRepository
                .findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException(String.format("No such feedback with id %d", feedbackId)));
    }

    private void saveFeedbackFromKafkaTopic(String key, FeedbackKafkaDto feedbackKafkaDto) {
        Feedback feedback = feedbackKafkaConverter.convertFromFeedbackDtoToFeedback(feedbackKafkaDto);
        var ticket = ticketService.getTicketByIdByEmail(feedbackKafkaDto.getTicketId(), feedbackKafkaDto.getOwnerEmail());
        feedback.setTicket(ticket);
        feedback.setUser(ticket.getOwner());
        feedbackRepository.save(feedback);
    }

    private User getUserFromSecurityContext() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private void processCallBackForFeedbackKafka(FeedbackKafkaDto feedbackKafkaDto,
                                                 ListenableFuture<SendResult<String, FeedbackKafkaDto>> futureTopic) {
        futureTopic.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(@NonNull Throwable ex) {
                System.out.println("unable to send message [" + feedbackKafkaDto + "] due to:" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, FeedbackKafkaDto> result) {
                System.out.println("sent message = [" + feedbackKafkaDto.toString() + "] with offset = [" + result.getRecordMetadata().offset() + "]");
            }
        });
    }
}
