package com.training.istasenka.validator.ticketcreationparams;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumFieldMatchValidator implements ConstraintValidator<EnumFieldMatch, Enum> {

    private List<String> enumValues;

    @Override
    public void initialize(EnumFieldMatch annotation) {
        enumValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Enum s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return enumValues.contains(s.name());
    }


}
