package com.example.projects.Entities;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProjectDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Date dateOfCreation;
    private Integer nbHoursMaxPerWeek;
    private Integer nbHoursPerWeek;
    private Role role;
    private String speciality;
}
