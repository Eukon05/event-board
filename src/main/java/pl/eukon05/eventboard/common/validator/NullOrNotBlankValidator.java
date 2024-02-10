package pl.eukon05.eventboard.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || !value.trim().isEmpty();
    }
}
