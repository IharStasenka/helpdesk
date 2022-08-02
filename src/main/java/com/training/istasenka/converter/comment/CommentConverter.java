package com.training.istasenka.converter.comment;

import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.CommentDto;
import com.training.istasenka.model.comment.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserConverter.class})
public interface CommentConverter {

    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    List<Comment> convertCommentDtoListToCommentList(List<CommentDto> commentDtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment convertCommentDtoToComment(CommentDto commentDto);

    CommentDto convertCommentToCommentDto(Comment comment);

    List<CommentDto> convertCommentListToCommentDtoList(List<Comment> comments);

}
