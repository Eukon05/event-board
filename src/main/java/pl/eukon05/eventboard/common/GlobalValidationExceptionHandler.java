package pl.eukon05.eventboard.common;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static pl.eukon05.eventboard.common.ResultWrapper.NO_CONTENT;

@ControllerAdvice
final class GlobalValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultWrapper<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ResultWrapper<String> wrapper = ResultWrapper.<String>builder().result(Result.VALIDATION_FAILED).data(NO_CONTENT).details(errors).build();
        return ResponseEntity.status(wrapper.getStatus()).body(wrapper);
    }

}
