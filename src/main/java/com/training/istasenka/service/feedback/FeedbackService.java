package com.training.istasenka.service.feedback;

import com.training.istasenka.dto.feedback.FeedbackKafkaDto;
import com.training.istasenka.model.feedback.Feedback;

public interface FeedbackService {

    Long saveFeedback(Feedback feedback, Long ticketId);

    Feedback getFeedback(Long ticketId, Long feedbackId);

    void saveFeedbackFromKafkaTopic(String key, FeedbackKafkaDto feedbackKafkaDto);
}
