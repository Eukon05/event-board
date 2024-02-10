package pl.eukon05.eventboard.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;
import java.util.Optional;

class CountryCodeValidator implements ConstraintValidator<CountryCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Optional.ofNullable(value).isEmpty() || value.isBlank() || value.length() > 2) return false;

        return Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2).contains(value);
    }
}
