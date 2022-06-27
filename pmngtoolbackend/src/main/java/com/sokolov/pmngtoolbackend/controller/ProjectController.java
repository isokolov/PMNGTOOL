package com.sokolov.pmngtoolbackend.controller;

import com.sokolov.pmngtoolbackend.entity.Project;
import com.sokolov.pmngtoolbackend.service.ProjectService;
import com.sokolov.pmngtoolbackend.service.ValidationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private ProjectService projectService;
    private ValidationRequestService validationRequestService;

    @Autowired
    public ProjectController(ProjectService projectService,
           ValidationRequestService validationRequestService) {
        this.projectService = projectService;
        this.validationRequestService = validationRequestService;
    }

    @PostMapping("/project")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = validationRequestService.validateService(result);
        if (errorMap != null) {
            return errorMap;
        }
        Project newProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> findProjectById(@PathVariable String projectId) {
        Project project = projectService.findProjectById(projectId);
        return new ResponseEntity(project, HttpStatus.CREATED);
    }

    @GetMapping("/project/all")
    public ResponseEntity<?> findAllProjects() {
        return new ResponseEntity(projectService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseEntity("Project with id: " + projectId + " was deleted", HttpStatus.CREATED);
    }

}
