package com.training.istasenka.model.user;


import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.customjwt.CustomJwt;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.util.UserRole;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Ticket> approverTickets;
    public static volatile ListAttribute<User, Ticket> ownerTickets;
    public static volatile ListAttribute<User, Comment> comments;
    public static volatile ListAttribute<User, CustomJwt> userTokens;
    public static volatile SingularAttribute<User, UserRole> role;
    public static volatile ListAttribute<User, Ticket> assigneeTickets;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> email;

    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";
    public static final String PASSWORD = "password";
    public static final String APPROVER_TICKETS = "approverTickets";
    public static final String OWNER_TICKETS = "ownerTickets";
    public static final String COMMENTS = "comments";
    public static final String USER_TOKENS = "userTokens";
    public static final String ROLE = "role";
    public static final String ASSIGNEE_TICKETS = "assigneeTickets";
    public static final String USER_ID = "userId";
    public static final String EMAIL = "email";
}
