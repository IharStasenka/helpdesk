package com.training.istasenka.provider.specification.pageable.pageable;

import com.training.istasenka.repository.pagiablecomponent.PageableEntity;
import com.training.istasenka.provider.specification.pageable.sort.SortProvider;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PaginationProviderImpl implements PageableProvider {

    private final SortProvider sortProvider;

    @Override
    public Pageable getPagination(Integer page, Integer size, String name, Direction direction, PageableEntity pageableEntity) {
        var sort
                = sortProvider.getSort(name , direction, pageableEntity);
        var pageRequest = getPageRequest(page, size, pageableEntity);
        return pageRequest.withSort(sort);
    }

    private boolean isNotPageable(Integer page, Integer size) {
        return (page == null) || (size == null);
    }

    private PageRequest getPageRequest(Integer page, Integer size, PageableEntity pageableEntity) {
        if (isNotPageable(page, size)) {
            return pageableEntity.getDefaultPageRequest();
        } else {
            return PageRequest.of(page, size);
        }
    }

}
