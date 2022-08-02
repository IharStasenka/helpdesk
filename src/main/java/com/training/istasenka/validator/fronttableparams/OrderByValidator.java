package com.training.istasenka.validator.fronttableparams;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;

public class OrderByValidator implements ConstraintValidator<OrderByMatch, String> {

    private Class<?> value;

    @Override
    public void initialize(OrderByMatch constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String orderBy, ConstraintValidatorContext constraintValidatorContext) {

        if ((orderBy == null) || orderBy.isEmpty()) {
            return true;
        } else {
            return Arrays.stream(
                    value.getDeclaredFields()).map(Field::getName).anyMatch(field -> field.equals(orderBy));
        }
    }
}
