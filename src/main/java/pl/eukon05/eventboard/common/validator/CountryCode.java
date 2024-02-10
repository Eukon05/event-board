package pl.eukon05.eventboard.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface CountryCode {
    String message() default "Invalid ISO country code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default false;
}
