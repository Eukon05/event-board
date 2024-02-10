package pl.eukon05.eventboard.openapi.annotation.param;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = "ID of the event", name = "id", required = true, example = "1")
public @interface IDParam {
}
