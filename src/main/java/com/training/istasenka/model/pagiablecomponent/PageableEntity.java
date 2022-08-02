package com.training.istasenka.model.pagiablecomponent;

import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.comment.Comment_;
import com.training.istasenka.model.history.History;
import com.training.istasenka.model.history.History_;
import com.training.istasenka.model.ticket.Ticket;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import static com.training.istasenka.model.ticket.Ticket_.DESIRED_RESOLUTION_DATE;
import static com.training.istasenka.model.ticket.Ticket_.URGENCY;

public enum PageableEntity {

    TICKET(Ticket.class, 0, 5, Sort.Direction.ASC, URGENCY) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForTickets();
        }
    },
    HISTORY(History.class, 0, 5, Sort.Direction.DESC, History_.DATE) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForHistories();
        }
    },
    COMMENT(Comment.class, 0, 5, Sort.Direction.DESC, Comment_.DATE) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForComments();
        }
    };

    private final Class<?> classForSorting;
    private final Integer page;
    private final Integer size;
    private final Sort.Direction direction;
    private final String fieldName;


    PageableEntity(Class<?> classForSorting, Integer page, Integer size, Sort.Direction direction, String fieldName) {
        this.classForSorting = classForSorting;
        this.page = page;
        this.size = size;
        this.direction = direction;
        this.fieldName = fieldName;
    }

    public Class<?> getClassForSorting() {
        return classForSorting;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Sort getDefaultSort() {
        return Sort.by(Sort.Direction.ASC);
    }

    public PageRequest getDefaultPageRequest() {
        return PageRequest.of(page, size);
    }

    public static PageableEntity getSortableEntity(Class<?> clazz) {
        switch (clazz.getSimpleName()) {
            case "Ticket":
                return TICKET;
            case "Comment":
                return COMMENT;
            case "History":
                return HISTORY;
            default: throw new CustomIllegalArgumentException("");
        }
    }

    private static Sort getDefaultSortForTickets() {
        var defaultOrders = new ArrayList<Sort.Order>();
        defaultOrders.add(new Sort.Order(Sort.Direction.ASC, URGENCY));
        defaultOrders.add(new Sort.Order(Sort.Direction.DESC, DESIRED_RESOLUTION_DATE));
        return Sort.by(defaultOrders);
    }

    private static Sort getDefaultSortForComments() {
        return Sort.by(new Sort.Order(Sort.Direction.DESC, Comment_.DATE));
    }

    private static Sort getDefaultSortForHistories() {
        return Sort.by(new Sort.Order(Sort.Direction.DESC, History_.DATE));
    }
}
