package com.example.projects.Controllers;

import com.example.projects.Entities.GroupProject;
import com.example.projects.Services.IGroupProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Group")
public class GroupController {
    private final IGroupProjectService groupProjectService;

    @PostMapping("/addgroup")
    public GroupProject addgroupAndAssignToproject(@RequestBody GroupProject groupProject, @RequestParam("id") Long id) {
        System.out.println("Received request with data: " + groupProject.toString());
        return groupProjectService.addgroupAndAssignToproject(groupProject, id);
    }

    @GetMapping("/listofgroups")
    public List<GroupProject> getAllgroups() {
        return groupProjectService.Getgroups();
    }

    @DeleteMapping("/Deletegroup/{id}")
    public void deletegroup(@PathVariable("id") Long id) {
        groupProjectService.removegroup(id);
    }

    @PutMapping("/updategroup")
    public GroupProject updategroup(@RequestBody GroupProject groupProject) {
        return groupProjectService.updategroup(groupProject);
    }

    @GetMapping("/getgroupbyid/{id}")
    public GroupProject getById(@PathVariable("id") Long id) {
        return groupProjectService.getById(id);
    }

    @PostMapping("/projects/assignStudentsToGroup")
    public ResponseEntity<String> assignStudentsToGroup(@RequestParam Long projectId) {
        try {
            // Check if the group is already assigned to the project
            if (groupProjectService.isGroupAssignedToProject(projectId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Group is already assigned to the project.");
            }

            // If the group is not assigned, proceed with assigning the group to the project
            groupProjectService.assignStudentsToGroup(projectId);
            return ResponseEntity.ok("Group assigned to project successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error assigning group to project: " + e.getMessage());
        }
    }

    @GetMapping("/userproject/{student_id}")
    public List<GroupProject> getProjectsForUser(@PathVariable Long student_id) {
        return groupProjectService.getProjectsForUser(student_id);
    }
}
