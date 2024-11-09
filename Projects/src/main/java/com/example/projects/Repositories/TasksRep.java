package com.example.projects.Repositories;

import com.example.projects.Entities.Project;
import com.example.projects.Entities.Status;
import com.example.projects.Entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRep extends JpaRepository<Tasks, Long> {

    List<Tasks> findTasksByProject(Project project);

    List<Tasks> findByProjectId(Long projectId);

    List<Tasks> findByStatus(Status status);
}
