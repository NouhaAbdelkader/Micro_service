package com.example.projects.Controllers;

import com.example.projects.Entities.Status;
import com.example.projects.Entities.Tasks;
import com.example.projects.Services.IProjectService;
import com.example.projects.Services.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Tasks")
public class TaskContoller {

    private final ITaskService iTaskService;
    private final IProjectService iProjectService;

    @PutMapping("/{id}/move")
    public void moveTask(@PathVariable Long id, @RequestParam Status newStatus) {
        iTaskService.moveTask(id, newStatus);
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<Tasks>> getTasksByProject(@PathVariable Long projectId) {
        List<Tasks> tasks = iTaskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/status/{status}")
    public List<Tasks> getTasksByStatus(@PathVariable Status status) {
        return iTaskService.getTasksByStatus(status);
    }
}
