package com.training.istasenka.model.comment;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public class Comment_ {

	public static volatile SingularAttribute<Comment, LocalDate> date;
	public static volatile SingularAttribute<Comment, Ticket> ticket;
	public static volatile SingularAttribute<Comment, Long> id;
	public static volatile SingularAttribute<Comment, String> text;
	public static volatile SingularAttribute<Comment, User> user;

	public static final String DATE = "date";
	public static final String TICKET = "ticket";
	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String USER = "user";

}

