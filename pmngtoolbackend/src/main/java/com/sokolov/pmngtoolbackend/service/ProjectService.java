package com.sokolov.pmngtoolbackend.service;

import com.sokolov.pmngtoolbackend.entity.Project;
import com.sokolov.pmngtoolbackend.exception.ProjectIdException;
import com.sokolov.pmngtoolbackend.repository.BacklogRepository;
import com.sokolov.pmngtoolbackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    public Project createProject(Project project) {
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
        if (optionalProject.isPresent()) {
            throw new ProjectIdException("Project with identifier " + optionalProject.get()
                .getProjectIdentifier() + " already exists");
        }

        return projectRepository.save(project);
    }

    public Project findProjectById(String projectId) {
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(projectId);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + projectId
                     + " doesn't exists");
        }
        return optionalProject.get();
    }

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId) {
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(projectId);
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + projectId
                    + " doesn't exists");
        }
        projectRepository.delete(optionalProject.get());
    }

    public Project updateProject(Project project) {
        Optional<Project> optionalProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
        if (optionalProject.isEmpty()) {
            throw new ProjectIdException("Project with identifier " + optionalProject.get()
                    .getProjectIdentifier() + " doesn't exist");
        }

        return projectRepository.save(project);
    }
}
