package com.training.istasenka.model.comment;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "COMMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Comment.user",
        attributeNodes = {
                @NamedAttributeNode(Comment_.USER)
        })
public class Comment {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate date;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;

    @Override
    public String toString() {
        return "Comment{" +
                "date=" + date +
                ", text='" + text + '\'' +
                ", user =" + user +
                ", ticket=" + ticket +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Comment cachedComment = (Comment) o;
        if (id == null) {
            return false;
        } else {
            return id.equals(cachedComment.id);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
