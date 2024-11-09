package com.example.projects.Services;

import com.example.projects.Entities.GroupProject;
import com.example.projects.Entities.Project;
import com.example.projects.Entities.UserProjectDTO;
import com.example.projects.Repositories.GroupProjectRep;
import com.example.projects.Repositories.ProjectRep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupProjectService implements IGroupProjectService {

    private final GroupProjectRep groupProjectRepo;
    private final ProjectRep projectRepo;

    @Autowired
    private final RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:4000/api/auth";



    @Override
    public List<GroupProject> Getgroups() {
        return groupProjectRepo.findAll();
    }

    @Override
    public GroupProject addgroupAndAssignToproject(GroupProject groupProject, long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("No Project Found with id " + projectId));
        groupProject.setProject(project);
        return groupProjectRepo.save(groupProject);
    }

    @Override
    public void removegroup(long id) {
        groupProjectRepo.deleteById(id);
    }

    @Override
    public GroupProject updategroup(GroupProject groupProject) {
        return groupProjectRepo.save(groupProject);
    }

    @Override
    public GroupProject getById(long id) {
        return groupProjectRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No group found with id " + id));
    }


    @Override
    public void assignStudentsToGroup(long projectId) {
        log.info("Starting to assign students to group for project ID: {}", projectId);

        // Step 1: Fetch Project
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
        log.info("Project found: {}", project);

        // Step 2: Gather Specialities
        List<String> specialities = new ArrayList<>(project.getSpecialities());
        if (specialities.isEmpty()) {
            throw new RuntimeException("No specialities found for project ID: " + projectId);
        }
        log.info("Specialities required for project: {}", specialities);

        // Step 3: Fetch Students by Speciality from User Service
        List<UserProjectDTO> availableStudents = new ArrayList<>();
        for (String speciality : specialities) {
            availableStudents.addAll(fetchUsersBySpecialityAndRole(speciality, "Student"));
            log.info("Students fetched for speciality {}: {}", speciality, availableStudents);
        }

        // Step 4: Create Group and Assign Students
        GroupProject group = new GroupProject();
        group.setName("Group for Project: " + project.getName());
        group.setProject(project);

        for (String speciality : specialities) {
            List<UserProjectDTO> studentsForSpeciality = availableStudents.stream()
                    .filter(student -> speciality.equalsIgnoreCase(student.getSpeciality()))
                    .collect(Collectors.toList());

            if (studentsForSpeciality.isEmpty()) {
                throw new RuntimeException("No students found with speciality: " + speciality);
            }

            group.addStudent(studentsForSpeciality.get(0)); // Assign one student for each speciality
            log.info("Assigned student with speciality {} to group {}", speciality, group.getName());
        }

        groupProjectRepo.save(group);
        log.info("Group assigned and saved successfully for project: {}", project.getName());
    }


    @Override
    public boolean isGroupAssignedToProject(long projectId) {
        return groupProjectRepo.existsByProjectId(projectId);
    }

    @Override
    public List<GroupProject> getProjectsForUser(long studentId) {
        return groupProjectRepo.findByStudentIdsContaining(studentId);
    }

    // Helper method to fetch users by speciality and role from user service
    private List<UserProjectDTO> fetchUsersBySpecialityAndRole(String speciality, String role) {
        String url = USER_SERVICE_URL + "/by-speciality-role?speciality=" + speciality + "&role=" + role;
        ResponseEntity<UserProjectDTO[]> response = restTemplate.getForEntity(url, UserProjectDTO[].class);

        // Convertir la réponse en liste
        return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
    }


    public GroupProject addStudent(UserProjectDTO student, GroupProject group) {
        if ("Student".equalsIgnoreCase(student.getRole().name())) {
            group.getStudentIds().add(student.getId()); // Add student ID to studentIds list
        } else {
            throw new IllegalArgumentException("Only users with the 'Student' role can be added to the group.");
        }

        return groupProjectRepo.save(group);
    }
}
