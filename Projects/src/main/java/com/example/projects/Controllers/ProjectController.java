package com.example.projects.Controllers;

import com.example.projects.Entities.Project;
import com.example.projects.Services.IProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/project/Project")
@Tag(name = "project/Project")
public class ProjectController {

    private final IProjectService iProjectService;

    @GetMapping("/listofProjects")
    public List<Project> getAllProjects() {
        return iProjectService.GetProject();
    }

    @PostMapping("/addProject")
    public Project addProject(@RequestBody Project project) {
        System.out.println("Received project: " + project);
        return iProjectService.addProject(project);
    }
    @PutMapping("/UpdatelProject")
    public Project updateProject(@RequestBody Project project) {
        return iProjectService.updateProject(project);
    }

    @DeleteMapping("/DeleteProject/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        iProjectService.removeProject(id);
    }

    @GetMapping("/getProjectbyid/{id}")
    public Project getById(@PathVariable("id") Long id) {
        return iProjectService.getById(id);
    }


}