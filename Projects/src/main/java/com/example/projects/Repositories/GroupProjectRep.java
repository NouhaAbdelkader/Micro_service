package com.example.projects.Repositories;

import com.example.projects.Entities.GroupProject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupProjectRep extends JpaRepository<GroupProject, Long> {

    boolean existsByProjectId(Long projectId);

    // Use the persisted field "studentIds" for the query
    List<GroupProject> findByStudentIdsContaining(Long studentId);


}