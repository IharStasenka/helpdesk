package com.training.istasenka.validator.category;

import com.training.istasenka.util.CategoryType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryValidator implements ConstraintValidator<CategoryMatch, String> {
    List<String> categories;

    @Override
    public void initialize(CategoryMatch constraintAnnotation) {
        categories = Arrays.stream(CategoryType.values()).map(CategoryType::getType).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return categories.contains(s);
    }
}
