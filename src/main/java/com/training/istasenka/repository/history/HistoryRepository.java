package com.training.istasenka.repository.history;

import com.training.istasenka.model.history.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor<History> {

    @Override
    @EntityGraph(value = "History.user")
    @NonNull
    Optional<History> findById(@Nullable Long id);

    @Override
    @EntityGraph(value = "History.user")
    @NonNull
    Page<History> findAll(@Nullable Specification<History> spec, @Nullable Pageable pageable);
}
