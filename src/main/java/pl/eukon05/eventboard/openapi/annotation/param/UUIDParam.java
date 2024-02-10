package pl.eukon05.eventboard.openapi.annotation.param;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = "UUID of the user", name = "id", required = true, example = "96972847-ab8b-47ac-b7de-cf4fd0e942d2")
public @interface UUIDParam {
}
