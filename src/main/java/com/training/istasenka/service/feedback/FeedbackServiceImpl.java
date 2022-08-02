package com.training.istasenka.service.feedback;

import com.training.istasenka.annotation.MailAudit;
import com.training.istasenka.converter.feedback.FeedbackKafkaConverter;
import com.training.istasenka.dto.feedback.FeedbackKafkaDto;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.exception.FeedbackNotFoundException;
import com.training.istasenka.model.feedback.Feedback;
import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.feedback.FeedbackRepository;
import com.training.istasenka.service.ticket.TicketService;
import com.training.istasenka.service.user.db.UserService;
import com.training.istasenka.util.StatusType;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;

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

    @Override
    @Transactional
    public Feedback getFeedback(Long ticketId, Long feedbackId) {
        ticketService.validateTicketResourceById(ticketId);
        return feedbackRepository
                .findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException(feedbackId));
    }

    @MailAudit
    @Transactional
    public void saveFeedbackFromKafkaTopic(String key, FeedbackKafkaDto feedbackKafkaDto) {
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
