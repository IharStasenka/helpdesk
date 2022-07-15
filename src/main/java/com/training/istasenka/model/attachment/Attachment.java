package com.training.istasenka.model.attachment;

import com.training.istasenka.model.ticket.Ticket;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "ATTACHMENT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "attachment_file")
    private byte[] file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
        Attachment cachedAttachment = (Attachment) o;
        if (id == null) {
            return false;
        } else {
            return id.equals(cachedAttachment.id);
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
