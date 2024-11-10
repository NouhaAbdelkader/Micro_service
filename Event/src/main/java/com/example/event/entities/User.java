package com.example.event.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String image;

    private String sexe;

    private LocalDate dateOfBirth;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    @Column(nullable = false)
    private Integer nbHoursMaxPerWeek = 0;

    @Column(nullable = false)
    private Integer nbHoursPerWeek = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String companyName;

    private String descriptionRecruiter;

    @Column(nullable = false)
    private Integer scoreXp = 0;

    @Column(nullable = false)
    private Integer level = 0;

    @Column(nullable = false)
    private Double overAllAverage = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Speciality speciality;

    @Column(nullable = false)
    private Boolean approved = false;

    @Column(nullable = false)
    private Integer validVoteCount = 0;

    @Column(nullable = false)
    private Boolean canVote = true;

    @Column(nullable = false)
    private Integer nbVoteForIncentives = 0;

    @Column(nullable = false)
    private Integer nbPrimeVoteForBadge = 0;

    private LocalDate paymentDay;

    private String hobbies;

    @ElementCollection(targetClass = Badge.class)
    @CollectionTable(name = "user_badges", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Badge> badges;

    /*@ManyToMany
    @JoinTable(
            name = "user_question_forums",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_forum_id")
    )
    private List<QuestionForum> questionForums;*/

    // Enum for Role
    public enum Role {
        TEACHER, STUDENT, MODERATOR, ADMIN, RECRUITER
    }

    // Enum for Speciality
    public enum Speciality {
        BD, ANGULAR, SPRING, DOTNET, RESEAU, IA, MOBILE, WEB, CLOUD, DEVOPS, SECURITY, DESIGN, MANAGEMENT, MARKETING, FINANCE
    }

    // Enum for Badge
    public enum Badge {
        GOLD, SILVER, BRONZE, DIMOND
    }
}
