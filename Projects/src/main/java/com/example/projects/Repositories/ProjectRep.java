package com.example.projects.Repositories;

import com.example.projects.Entities.Project;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;
import java.util.List;

public interface ProjectRep extends JpaRepository<Project, Long> {


    Project getById(Long id);
}
