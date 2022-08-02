package com.training.istasenka.model.ticket;

import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.category.Category;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.history.History;
import com.training.istasenka.model.user.User;
import com.training.istasenka.util.StatusType;
import com.training.istasenka.util.UrgencyType;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ticket.class)
public class Ticket_ {
    public static volatile SingularAttribute<Ticket, Long> id;
    public static volatile SingularAttribute<Ticket, String> name;
    public static volatile SingularAttribute<Ticket, String> description;
    public static volatile SingularAttribute<Ticket, LocalDate> createdOn;
    public static volatile SingularAttribute<Ticket, LocalDate> desiredResolutionDate;
    public static volatile SingularAttribute<Ticket, User> assignee;
    public static volatile SingularAttribute<Ticket, User> owner;
    public static volatile SingularAttribute<Ticket, User> approver;
    public static volatile SingularAttribute<Ticket, UrgencyType> urgency;
    public static volatile SingularAttribute<Ticket, StatusType> status;
    public static volatile SingularAttribute<Ticket, Category> category;
    public static volatile ListAttribute<Ticket, Attachment> attachments;
    public static volatile ListAttribute<Ticket, Comment> comments;
    public static volatile ListAttribute<Ticket, History> histories;


    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_ON = "createdOn";
    public static final String DESIRED_RESOLUTION_DATE = "desiredResolutionDate";
    public static final String ASSIGNEE = "assignee";
    public static final String OWNER = "owner";
    public static final String APPROVER = "approver";
    public static final String URGENCY = "urgency";
    public static final String STATUS = "status";
    public static final String CATEGORY = "category";
    public static final String ATTACHMENTS = "attachments";
    public static final String COMMENTS = "comments";
    public static final String HISTORIES = "histories";
}