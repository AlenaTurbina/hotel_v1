package com.hotel_server.exceptionHandler;

import com.hotel_server.exceptionHandler.error.ApiError;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.message.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to configure ExceptionHandlers
 * Annotation @RestControllerAdvice a convenience annotation that is itself annotated with @ControllerAdvice and @ResponseBody.
 *
 * @author Alena Turbina
 */

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerValidation {

    /**
     * Method for MethodArgumentNotValidException
     *
     * @param exception - exception to be thrown when validation on an argument annotated with @Valid fails
     * @return Map of error-field and error-message, HttpStatus
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = Messages.getMessage(error.getCode());
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    @ExceptionHandler({EntityNotFoundException.class, ServerEntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundExceptions(
            RuntimeException exception, HttpServletRequest request, HttpServletResponse response) {
        ApiError apiError = new ApiError("Entity Not Found Exception", exception.getMessage());
        log.error("EntityNotFoundExceptions: " + exception.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(
            Exception exception, HttpServletRequest request, HttpServletResponse response) {
        ApiError apiError = new ApiError("Object Not Found Exception", exception.getMessage());
        log.error("EntityNotFoundExceptions: " + exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
