package com.sokolov.pmngtoolbackend.repository;

import com.sokolov.pmngtoolbackend.entity.Backlog;
import com.sokolov.pmngtoolbackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String Identifier);
    List<Task> findAllByProjectIdentifier(String Identifier);
}