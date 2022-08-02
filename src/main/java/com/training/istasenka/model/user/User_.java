package com.training.istasenka.model.user;


import com.training.istasenka.util.UserRole;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, UserRole> role;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> email;

    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";
    public static final String ROLE = "role";
    public static final String USER_ID = "userId";
    public static final String EMAIL = "email";
}
