package com.management.userManagement.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int age;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.age = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= age;
    }
}