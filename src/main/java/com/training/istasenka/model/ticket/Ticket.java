package com.training.istasenka.model.ticket;

import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.category.Category;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.history.History;
import com.training.istasenka.model.user.User;
import com.training.istasenka.util.StatusType;
import com.training.istasenka.util.UrgencyType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TICKETS")
@NamedEntityGraph(name = "Ticket.comments",
        attributeNodes = {
                @NamedAttributeNode(Ticket_.CATEGORY),
                @NamedAttributeNode(Ticket_.ASSIGNEE),
                @NamedAttributeNode(Ticket_.APPROVER),
                @NamedAttributeNode(Ticket_.OWNER),
                @NamedAttributeNode(Ticket_.ATTACHMENTS)
        }
)
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @GenericGenerator(name = "ID_GENERATOR",
            strategy = "increment",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "HD_SEQUENCE"
                    )
            })
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATED_ON", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "DESIRED_RESOLUTION_DATE", nullable = false)
    private LocalDate desiredResolutionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User approver;

    @Column(name = "urgency_id", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UrgencyType urgency;

    @Column(name = "status_id", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "ticket", cascade = {PERSIST, MERGE}, orphanRemoval = true)
    private List<Attachment> attachments = new LinkedList<>();

    @OneToMany(mappedBy = "ticket", cascade = {PERSIST, MERGE}, orphanRemoval = true)
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "ticket", cascade = PERSIST, orphanRemoval = true)
    private List<History> histories = new LinkedList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addComments(List<Comment> commentList) {
        comments.addAll(commentList);
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
        attachment.setTicket(this);
    }

    public void addAttachments(List<Attachment> attachmentList) {
        if (attachments == null) {
            attachments = new LinkedList<>();
        }
        attachmentList.forEach(attachment -> {
            attachment.setTicket(this);
            attachments.add(attachment);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Ticket cachedTicket = (Ticket) o;
        if (id == null) {
            return false;
        } else {
            return id.equals(cachedTicket.id);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", desiredResolutionDate=" + desiredResolutionDate +
                ", assignee=" + assignee +
                ", owner=" + owner +
                ", approver=" + approver +
                ", urgency=" + urgency +
                ", status=" + status +
                ", category=" + category +
                ", attachments=" + attachments +
                ", comments=" + comments +
                '}';
    }
}


