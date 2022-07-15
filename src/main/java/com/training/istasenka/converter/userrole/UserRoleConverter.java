package com.training.istasenka.converter.userrole;

import com.training.istasenka.util.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter()
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.getShortRoleName();
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        return UserRole.fromShortUserName(s);
    }
}
