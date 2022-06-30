package com.sokolov.pmngtoolbackend.service;

import com.sokolov.pmngtoolbackend.entity.Backlog;
import com.sokolov.pmngtoolbackend.entity.Project;
import com.sokolov.pmngtoolbackend.entity.Task;
import com.sokolov.pmngtoolbackend.exception.ProjectIdException;
import com.sokolov.pmngtoolbackend.repository.BacklogRepository;
import com.sokolov.pmngtoolbackend.repository.ProjectRepository;
import com.sokolov.pmngtoolbackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private BacklogRepository backlogRepository;

    @Autowired
    public TaskService(ProjectRepository projectRepository,
                       TaskRepository taskRepository,
                       BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.backlogRepository = backlogRepository;
    }

    public Iterable<Task>findBacklogById(String id){
        return taskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public Task addTaskToBacklog(String projectId, Task task){

        //Exceptions: Project not found
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(projectId);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + projectId
                    + " doesn't exists");
        }
        // Tasks to be added to a specific project, project != null, Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectId);
        //set the backlog to task
        task.setBacklog(backlog);
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer BacklogSequence = backlog.getTaskSequence();
        BacklogSequence++;
        // Update the BL SEQUENCE
        backlog.setTaskSequence(BacklogSequence);

        //Add Sequence to Project Task
        task.setProjectSequence(projectId +"-" + BacklogSequence);
        task.setProjectIdentifier(projectId);

        // INITIAL status when status is null
        if(task.getStatus() == "" || task.getStatus()==null) {
            task.setStatus("TO_DO");
        }

        if(task.getPriority() == null) {
            task.setPriority(3);
        }

        return taskRepository.save(task);
    }

    public Iterable<Task> getAllTasks(String backlog_id) {
        //Exceptions: Project not found
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(backlog_id);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + backlog_id
                    + " doesn't exists");
        }
        return taskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
