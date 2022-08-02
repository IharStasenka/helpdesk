package com.training.istasenka.model.history;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "HISTORIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Immutable
@NamedEntityGraph(name = "History.user",
        attributeNodes = {
                @NamedAttributeNode(History_.USER)
        })
public class History {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private String action;

    @Column(nullable = false, updatable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID", nullable = false, updatable = false)
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        History cachedHistory = (History) o;
        if (id == null) {
            return false;
        } else {
            return id.equals(cachedHistory.id);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action);
    }

}
