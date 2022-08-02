package com.training.istasenka.model.feedback;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FEEDBACKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate date;

    @Column(updatable = false)
    private String text;

    @Column(nullable = false, updatable = false)
    @Max(value = 5)
    @Min(value = 1)
    private Long rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Feedback cachedFeedback = (Feedback) o;
        if (id == null) {
            return false;
        } else {
            return id.equals(cachedFeedback.id);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
