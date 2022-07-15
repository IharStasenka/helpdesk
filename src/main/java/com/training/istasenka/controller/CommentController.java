package com.training.istasenka.controller;

import com.training.istasenka.assembler.CommentsDtoAssembler;
import com.training.istasenka.converter.comment.CommentConverter;
import com.training.istasenka.dto.CommentDto;
import com.training.istasenka.dto.validationgroups.CreateInfo;
import com.training.istasenka.dto.validationgroups.UpdateInfo;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.repository.pagiablecomponent.PageableEntity;
import com.training.istasenka.provider.specification.pageable.pageable.PageableProvider;
import com.training.istasenka.service.comment.CommentService;
import com.training.istasenka.validator.fronttableparams.OrderByMatch;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
@EnableHypermediaSupport(type = HAL)
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;
    private final CommentsDtoAssembler commentsDtoAssembler;
    private final PageableProvider pageableProvider;
    private final PagedResourcesAssembler<Comment> commentPagedResourcesAssembler;


    @GetMapping(path = "/{ticket_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> getById(
            @PathVariable(value = "comment_id") Long commentId,
            @PathVariable(value = "ticket_id") Long ticketId) {
        var comment = commentService.findCommentById(commentId, ticketId);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(commentsDtoAssembler.toModel(comment));
    }


    @DeleteMapping(path = "/{ticket_id}/comments/{comment_id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "comment_id") Long commentId,
            @PathVariable(value = "ticket_id") Long ticketId) {
        commentService.deleteCommentById(commentId, ticketId);

        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }

    @PostMapping(path = "/{ticket_id}/comments")
    public ResponseEntity<Long> save(
            @PathVariable(value = "ticket_id") Long ticketId,
            @RequestBody @Validated(CreateInfo.class) CommentDto commentDto) {
        var createdCommentId
                = commentService.saveComment(commentConverter.convertCommentDtoToComment(commentDto), ticketId);
        var commentUri = linkTo(methodOn(CommentController.class).save(ticketId, commentDto))
                .slash(createdCommentId)
                .toUri()
                .toASCIIString();

        return ResponseEntity.status(CREATED)
                .header(HttpHeaders.LOCATION, commentUri)
                .body(createdCommentId);

    }

    @GetMapping(path = "/{ticket_id}/comments")
    public ResponseEntity<PagedModel<CommentDto>> getAllByTicketId(
            @PathVariable(value = "ticket_id") Long ticketId,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "rowsPerPage") Integer rowsPerPage,
            @OrderByMatch(value = CommentDto.class) @RequestParam(name = "orderBy") String orderBy,
            @RequestParam(name = "order") Sort.Direction order) {
        var pagination = pageableProvider.getPagination(page, rowsPerPage, orderBy, order, PageableEntity.COMMENT);
        var commentPage = commentService.findPageOfCommentsForTicket(ticketId, pagination);
        var httpStatus = PARTIAL_CONTENT;
        if (commentPage.getTotalPages() == 1) {
            httpStatus = OK;
        }
        if (commentPage.isEmpty()) {
            httpStatus = REQUESTED_RANGE_NOT_SATISFIABLE;
        }

        return ResponseEntity
                .status(httpStatus)
                .contentType(APPLICATION_JSON)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", String.valueOf(commentPage.getTotalElements()))
                .body(commentPagedResourcesAssembler.toModel(commentPage, commentsDtoAssembler));
    }

    @PutMapping(path = "/{ticket_id}/comments/{comment_id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "ticket_id") Long ticketId,
            @PathVariable(value = "comment_id") Long commentId,
            @RequestBody @Validated(UpdateInfo.class) CommentDto commentDto) {
        commentService.updateCommentById(commentConverter.convertCommentDtoToComment(commentDto), commentId, ticketId);

        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }
}
