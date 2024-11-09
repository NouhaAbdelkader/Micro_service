package com.example.projects.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "group_projects")
public class GroupProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "group_students", joinColumns = @JoinColumn(name = "group_project_id"))
    @Column(name = "student_id")
    private List<Long> studentIds = new ArrayList<>();

    @Transient
    private List<UserProjectDTO> students; // This list will be populated with detailed user info from the User service

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "groupProject",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Tasks> tasks = new ArrayList<>();

    public void addStudent(UserProjectDTO student) {
        if (student.getRole() == Role.Student) {
            studentIds.add(student.getId());
        } else {
            throw new IllegalArgumentException("Only users with role 'Student' can be added to the group.");
        }
    }
}