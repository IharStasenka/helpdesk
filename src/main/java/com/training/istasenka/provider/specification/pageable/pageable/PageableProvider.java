package com.training.istasenka.provider.specification.pageable.pageable;

import com.training.istasenka.repository.pagiablecomponent.PageableEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableProvider {
    Pageable getPagination(Integer page, Integer size, String name, Sort.Direction direction, PageableEntity pageableEntity);

}
