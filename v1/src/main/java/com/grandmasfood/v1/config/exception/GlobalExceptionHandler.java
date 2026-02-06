package com.grandmasfood.v1.config.exception;

import com.grandmasfood.v1.config.enums.ErrorCodeEnum;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.exception.SameDataRequestComparedToDBException;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiErrorResponse buildApiError(ErrorCodeEnum codeEnum, String description, String exceptionName){
        return new ApiErrorResponse(
                codeEnum,
                Instant.now(),
                description,
                exceptionName
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SpringValidationErrorResponse> handleSpringValidationException(MethodArgumentNotValidException ex){
        HashMap<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new SpringValidationErrorResponse(
                        ErrorCodeEnum.ERR002,
                        Instant.now(),
                        "Bad request, spring validation found the following errors: ",
                        ex.getClass().getSimpleName(),
                        errors
                ));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> hanleEntityAlreadyExistsExeption(EntityAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildApiError(
                        ErrorCodeEnum.ERR001,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildApiError(
                        ErrorCodeEnum.ERR003,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildApiError(
                        ErrorCodeEnum.ERR004,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidatedControllerAnotation(ConstraintViolationException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildApiError(
                        ErrorCodeEnum.ERR005,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(SameDataRequestComparedToDBException.class)
    public ResponseEntity<ApiErrorResponse> handleSameDataRequest(SameDataRequestComparedToDBException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildApiError(
                        ErrorCodeEnum.ERR006,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleUUIDValidatorException(MethodArgumentNotValidException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildApiError(
                        ErrorCodeEnum.ERR007,
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                ));
    }
}
