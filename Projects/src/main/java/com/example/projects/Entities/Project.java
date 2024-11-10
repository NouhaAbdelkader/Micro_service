package com.example.projects.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Add constraints if needed
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "project_specialities", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "speciality")
    private Set<String> specialities;
    private LocalDate datedebut;

    private LocalDate deadline;
    private int number;

    private boolean hasGroupProject = false;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "project-tasks")
    private List<Tasks> tasks;
}