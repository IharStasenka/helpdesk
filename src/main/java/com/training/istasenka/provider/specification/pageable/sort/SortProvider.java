package com.training.istasenka.provider.specification.pageable.sort;

import com.training.istasenka.model.pagiablecomponent.PageableEntity;
import org.springframework.data.domain.Sort;

public interface SortProvider {

    Sort getSort(String name, Sort.Direction direction, PageableEntity pageableEntity);
}
