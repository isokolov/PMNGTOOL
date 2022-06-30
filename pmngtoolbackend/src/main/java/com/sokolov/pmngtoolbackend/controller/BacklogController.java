package com.sokolov.pmngtoolbackend.controller;

import com.sokolov.pmngtoolbackend.entity.Backlog;
import com.sokolov.pmngtoolbackend.entity.Task;
import com.sokolov.pmngtoolbackend.service.BacklogService;
import com.sokolov.pmngtoolbackend.service.TaskService;
import com.sokolov.pmngtoolbackend.service.ValidationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private TaskService taskService;
    private BacklogService backlogService;
    private ValidationRequestService validationRequestService;

    @Autowired
    public BacklogController(TaskService taskService, ValidationRequestService validationRequestService,
         BacklogService backlogService) {
        this.taskService = taskService;
        this.validationRequestService = validationRequestService;
        this.backlogService = backlogService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addTaskToBacklog(@Valid @RequestBody Task task,
                      BindingResult result, @PathVariable String backlog_id) {
        ResponseEntity<?> errorMap = validationRequestService.validateService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Task projectTask = taskService.addTaskToBacklog(backlog_id, task);

        return new ResponseEntity<Task>(projectTask, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<Task> getAllTasks(@PathVariable String backlog_id){
        return taskService.getAllTasks(backlog_id);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<Task> getProjectBacklog(@PathVariable String backlog_id){
        return taskService.findBacklogById(backlog_id);
    }
}
