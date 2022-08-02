package com.training.istasenka.model.attachment;

import com.training.istasenka.model.ticket.Ticket;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attachment.class)
public class Attachment_ {

	public static volatile SingularAttribute<Attachment, byte[]> file;
	public static volatile SingularAttribute<Attachment, Ticket> ticket;
	public static volatile SingularAttribute<Attachment, String> name;
	public static volatile SingularAttribute<Attachment, Long> id;

	public static final String FILE = "file";
	public static final String TICKET = "ticket";
	public static final String NAME = "name";
	public static final String ID = "id";

}

