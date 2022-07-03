package com.sokolov.pmngtoolbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> createProjectIdExceptionResponse(ProjectIdException ex,
                                                                   WebRequest request) {
        ProjectExceptionResponse response = new ProjectExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> createTaskExceptionResponse(TaskException tex,
                                                              WebRequest request) {
        TaskExceptionResponse taskExceptionResponse =
                new TaskExceptionResponse(tex.getMessage());
        return new ResponseEntity<>(taskExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
