package com.epam.spring.testingapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[А-їA-z`']{2,}$");
    }
}
