package com.training.istasenka.provider.specification.pageable.sort;

import com.training.istasenka.model.pagiablecomponent.PageableEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class SortProviderImpl implements SortProvider {

    @Override
    public Sort getSort(String name, Direction direction, PageableEntity pageableEntity) {

        if (direction == null || name == null || name.isEmpty()) {
            return pageableEntity.getDefaultSort();
        }
        return Sort.by(direction, name);
    }
}
