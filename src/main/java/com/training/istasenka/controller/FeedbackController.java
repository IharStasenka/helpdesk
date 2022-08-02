package com.training.istasenka.controller;


import com.training.istasenka.converter.feedback.FeedbackConverter;
import com.training.istasenka.dto.feedback.FeedbackDto;
import com.training.istasenka.service.feedback.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("tickets")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackConverter feedbackConverter;

    @PostMapping(path = "/{ticket_id}/feedbacks")
    public ResponseEntity<Long> save(
            @RequestBody FeedbackDto feedbackDto,
            @PathVariable(value = "ticket_id") Long ticketId) {
        var feedback = feedbackConverter.convertFeedbackDtoToFeedBack(feedbackDto);
        var cachedFeedbackId = feedbackService.saveFeedback(feedback, ticketId);
        var feedbackUri = linkTo(methodOn(FeedbackController.class).save(feedbackDto, ticketId))
                .slash(cachedFeedbackId)
                .toUri()
                .toASCIIString();
        return ResponseEntity
                .status(CREATED)
                .header(HttpHeaders.LOCATION, feedbackUri)
                .body(cachedFeedbackId);
    }

    @GetMapping(path = "/{ticket_id}/feedbacks/{feedback_id}")
    public ResponseEntity<FeedbackDto> getById(
            @PathVariable(value = "ticket_id") Long ticketId,
            @PathVariable(value = "feedback_id") Long feedbackId) {
        var feedback = feedbackService.getFeedback(ticketId, feedbackId);
        return ResponseEntity.status(OK).body(feedbackConverter.convertFeedbackToFeedbackDto(feedback));
    }
}
