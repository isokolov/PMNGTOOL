package com.sokolov.pmngtoolbackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskExceptionResponse {

    private String taskIdentifier;

    public TaskExceptionResponse(String taskIdentifier) {
        this.taskIdentifier = taskIdentifier;
    }
}
