package com.sokolov.pmngtoolbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer TaskSequence = 0;
    private String projectIdentifier;

    // OneToOne with project
    @OneToOne(mappedBy = "backlog")
    @JsonIgnore
    private Project project;

    // OneToMany tasks
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "backlog",
    orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();


}