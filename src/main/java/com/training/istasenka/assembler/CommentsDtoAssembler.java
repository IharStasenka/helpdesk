package com.training.istasenka.assembler;

import com.training.istasenka.controller.CommentController;
import com.training.istasenka.converter.comment.CommentConverter;
import com.training.istasenka.dto.CommentDto;
import com.training.istasenka.model.comment.Comment;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentsDtoAssembler extends RepresentationModelAssemblerSupport<Comment, CommentDto> {

    private final CommentConverter commentConverter;

    public CommentsDtoAssembler(CommentConverter commentConverter) {
        super(CommentController.class, CommentDto.class);
        this.commentConverter = commentConverter;
    }

    @Override
    @NonNull
    public CommentDto toModel(@NonNull Comment entity) {
        var commentDto = commentConverter.convertCommentToCommentDto(entity);
        return commentDto
                .add(linkTo(methodOn(CommentController.class).getById(entity.getId(), entity.getTicket().getId())).withSelfRel());
    }

    public List<CommentDto> toModelList(List<Comment> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
