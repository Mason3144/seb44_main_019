package com.shellwe.back.utils.customannotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EachSizeValidator implements ConstraintValidator<EachSize, List<String>> {

    private int max;
    private int min;

    @Override
    public void initialize(EachSize constraintAnnotation) {
        max = constraintAnnotation.max();
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }
        for (String value : values) {
            if (value == null || value.length() < min || value.length() > max) {
                return false;
            }
        }
        return true;
    }
}