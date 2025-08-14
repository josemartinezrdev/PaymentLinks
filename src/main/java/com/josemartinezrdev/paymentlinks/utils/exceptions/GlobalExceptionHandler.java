package com.josemartinezrdev.paymentlinks.utils.exceptions;

import java.util.Collections;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.josemartinezrdev.paymentlinks.domain.dtos.ErrorResponseDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @info: 401
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "https://errors.example.com/unauthorized",
                "Unauthorized",
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                "UNAUTHORIZED",
                Collections.emptyMap());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // @info: 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "https://errors.example.com/forbidden",
                "Forbidden",
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                "FORBIDDEN",
                Collections.emptyMap());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // @info: 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(EntityNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "https://errors.example.com/not_found",
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "NOT_FOUND",
                Collections.emptyMap());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // @info: 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(DataIntegrityViolationException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "https://errors.example.com/conflict",
                "Conflict",
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                "CONFLICT",
                Collections.emptyMap());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // @info: 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "https://errors.example.com/validation_error",
                "Validation Error",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Some fields are invalid",
                "VALIDATION_ERROR",
                null
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // @info: 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleInternalError(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "https://errors.example.com/internal_error",
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                "INTERNAL_ERROR",
                Collections.emptyMap());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
