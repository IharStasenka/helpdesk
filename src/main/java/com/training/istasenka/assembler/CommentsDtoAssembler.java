package com.training.istasenka.assembler;

import com.training.istasenka.controller.CommentController;
import com.training.istasenka.converter.comment.CommentConverter;
import com.training.istasenka.dto.CommentDto;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.provider.link.LinkProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentsDtoAssembler extends RepresentationModelAssemblerSupport<Comment, CommentDto> {

    private final CommentConverter commentConverter;
    private final LinkProvider linkProvider;

    public CommentsDtoAssembler(CommentConverter commentConverter, LinkProvider linkProvider) {
        super(CommentController.class, CommentDto.class);
        this.commentConverter = commentConverter;
        this.linkProvider = linkProvider;
    }

    @Override
    @NonNull
    public CommentDto toModel(@NonNull Comment entity) {
        var commentDto = commentConverter.convertCommentToCommentDto(entity);
        return commentDto.add(linkProvider.getCommentLink(entity.getTicket().getId(), entity.getId()));
    }

    public List<CommentDto> toModelList(List<Comment> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
