package com.example.projects.Services;

import com.example.projects.Entities.Project;
import com.example.projects.Entities.Tasks;
import com.example.projects.Repositories.GroupProjectRep;
import com.example.projects.Repositories.ProjectRep;
import com.example.projects.Repositories.TasksRep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
    private final ProjectRep projectRepo;
    private final GroupProjectRep groupProjectRepo;
    private final TasksRep tasksRepo;

    @Override
    public List<Project> GetProject() {
        List<Project> projects = projectRepo.findAll();
        for (Project project : projects) {
            boolean hasGroupProject = groupProjectRepo.existsByProjectId(project.getId());
            project.setHasGroupProject(hasGroupProject);

            // Fetch tasks associated with the project
            List<Tasks> tasks = tasksRepo.findTasksByProject(project);
            project.setTasks(tasks);
        }
        return projects;
    }


    @Override
    public Project addProject(Project project) {
        // Save the project first to generate an ID
        Project savedProject = projectRepo.save(project);

        // Get the list of tasks associated with the project
        List<Tasks> tasksList = project.getTasks();

        if (tasksList != null && !tasksList.isEmpty()) {
            // Set the project reference for each task
            for (Tasks task : tasksList) {
                task.setProject(savedProject);
            }

            // Save all tasks after setting the project reference
            tasksList = tasksRepo.saveAll(tasksList);
        }

        // Update the project to include the saved tasks
        savedProject.setTasks(tasksList);

        // Return the saved project with associated tasks
        return projectRepo.save(savedProject);
    }

    @Override
    public void removeProject(Long id) {
        projectRepo.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public Project getById(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No project found with id " + id));
    }
}