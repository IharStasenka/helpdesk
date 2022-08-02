package com.training.istasenka.repository.feedback;

import com.training.istasenka.model.feedback.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Override
    @NonNull
    Optional<Feedback> findById(@NonNull Long aLong);
}
