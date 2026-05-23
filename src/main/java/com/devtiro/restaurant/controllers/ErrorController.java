package com.devtiro.restaurant.controllers;

import com.devtiro.restaurant.domain.dtos.ErrorDto;
import com.devtiro.restaurant.exceptions.BaseException;
import com.devtiro.restaurant.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        String errorMsg=ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorDto errorDto=ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMsg)
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorDto> handleStorageException(StorageException ex){
        log.error("StorageException",ex);
        ErrorDto errorDto=ErrorDto.builder()
                .status(500)
                .message("Unable to save or retrieve the photo. Please try again later.")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleBaseException(BaseException ex){
        log.error("Base Exception",ex);
        ErrorDto errorDto=ErrorDto.builder()
                .status(500)
                .message("Unable to save or retrieve the photo. Please try again later.")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        log.error("caught unExpected Exception",ex);
        ErrorDto errorDto=ErrorDto.builder()
                .status(500)
                .message("Unable to save or retrieve the photo. Please try again later.")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
