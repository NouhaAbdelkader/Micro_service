package com.example.projects.Services;

import com.example.projects.Entities.Project;

import java.util.List;

public interface IProjectService {

    List<Project> GetProject();
    Project addProject(Project project) ;
    void removeProject(Long id);
    Project updateProject(Project project);
    Project getById(Long id);

}
