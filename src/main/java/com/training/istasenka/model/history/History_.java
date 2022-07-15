package com.training.istasenka.model.history;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(History.class)
public class History_ {

    public static volatile SingularAttribute<History, LocalDate> date;
    public static volatile SingularAttribute<History, Ticket> ticket;
    public static volatile SingularAttribute<History, Long> id;
    public static volatile SingularAttribute<History, String> action;
    public static volatile SingularAttribute<History, User> user;
    public static volatile SingularAttribute<History, String> description;

    public static final String DATE = "date";
    public static final String TICKET = "ticket";
    public static final String ID = "id";
    public static final String ACTION = "action";
    public static final String USER = "user";
    public static final String DESCRIPTION = "description";

}
