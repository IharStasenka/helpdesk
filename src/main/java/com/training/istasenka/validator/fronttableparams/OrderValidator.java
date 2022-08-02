package com.training.istasenka.validator.fronttableparams;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<OrderMatch, String> {

    @Value("${frontPanel.table.asc}")
    private String asc;

    @Value("${frontPanel.table.desc}")
    private String desc;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.equals("asc") || s.equals("desc") || s.isBlank();
    }
}
