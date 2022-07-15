package com.training.istasenka.model.customjwt;

import com.training.istasenka.model.user.User;
import com.training.istasenka.util.TokenType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CustomJwt.class)
public class CustomJwt_ {

	public static volatile SingularAttribute<CustomJwt, Date> expiryDate;
	public static volatile SingularAttribute<CustomJwt, String> jwtTokenData;
	public static volatile SingularAttribute<CustomJwt, Long> numberChildToken;
	public static volatile SingularAttribute<CustomJwt, Long> id;
	public static volatile SingularAttribute<CustomJwt, TokenType> tokenType;
	public static volatile SingularAttribute<CustomJwt, User> user;

	public static final String EXPIRY_DATE = "expiryDate";
	public static final String JWT_TOKEN_DATA = "jwtTokenData";
	public static final String NUMBER_CHILD_TOKEN = "numberChildToken";
	public static final String ID = "id";
	public static final String TOKEN_TYPE = "tokenType";
	public static final String USER = "user";

}

