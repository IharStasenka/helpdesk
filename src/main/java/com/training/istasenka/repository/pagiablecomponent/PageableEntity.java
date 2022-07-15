package com.training.istasenka.repository.pagiablecomponent;

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

    TICKET(Ticket.class) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForTickets();
        }
    },
    HISTORY(History.class) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForHistories();
        }
    },
    COMMENT(Comment.class) {
        @Override
        public Sort getDefaultSort() {
            return getDefaultSortForComments();
        }
    };

    private final Class<?> classForSorting;


    PageableEntity(Class<?> classForSorting) {
        this.classForSorting = classForSorting;
    }

    public Class<?> getClassForSorting() {
        return classForSorting;
    }

    public Sort getDefaultSort() {
        return Sort.by(Sort.Direction.ASC);
    }

    public PageRequest getDefaultPageRequest() {
        return PageRequest.of(0, 100);
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
