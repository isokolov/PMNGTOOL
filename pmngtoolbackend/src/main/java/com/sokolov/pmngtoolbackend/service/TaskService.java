package com.sokolov.pmngtoolbackend.service;

import com.sokolov.pmngtoolbackend.entity.Backlog;
import com.sokolov.pmngtoolbackend.entity.Project;
import com.sokolov.pmngtoolbackend.entity.Task;
import com.sokolov.pmngtoolbackend.exception.ProjectIdException;
import com.sokolov.pmngtoolbackend.exception.TaskException;
import com.sokolov.pmngtoolbackend.repository.BacklogRepository;
import com.sokolov.pmngtoolbackend.repository.ProjectRepository;
import com.sokolov.pmngtoolbackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Task findTaskByProjectSequence(String backlog_id, String task_id){

        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(backlog_id);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + backlog_id
                    + " doesn't exists");
        }
        Optional<Task> optTask = taskRepository.findByProjectSequence(task_id);
        if (optTask.isEmpty()) {
            throw new TaskException(("Task with taskSequence: " + task_id + " doesn't exist"));
        }

        return optTask.get();
    }

    public Task updateTaskByProjectSequence(Task updatedTask, String backlog_id, String task_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(backlog_id);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + backlog_id
                    + " doesn't exists");
        }

        Optional<Task> optTask = taskRepository.findByProjectSequence(task_id);
        if (optTask.isEmpty()) {
            throw new TaskException(("Task with taskSequence: " + task_id + " doesn't exist"));
        }
        // copy values from updatedTask to the new Task
        Task projectTask = optTask.get();

        projectTask = updatedTask;

        return taskRepository.save(projectTask);
    }

    public void deleteTaskByProjectSequence(String backlog_id, String pt_id){
        Task projectTask = findTaskByProjectSequence(backlog_id, pt_id);

        /* Backlog backlog = projectTask.getBacklog();
        List<Task> pts = backlog.getTasks();
        pts.remove(projectTask);
        backlogRepository.save(backlog); */

        taskRepository.delete(projectTask);
    }
}
