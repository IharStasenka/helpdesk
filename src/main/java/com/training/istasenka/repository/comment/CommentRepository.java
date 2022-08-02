package com.training.istasenka.repository.comment;

import com.training.istasenka.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {


   @Override
   @NonNull
   @EntityGraph(value = "Comment.user")
   Page<Comment> findAll(@Nullable Specification<Comment> spec, @NonNull Pageable pageable);

   @Override
   @NonNull
   @EntityGraph(value = "Comment.user")
   Optional<Comment> findById(@NonNull Long id);

}
