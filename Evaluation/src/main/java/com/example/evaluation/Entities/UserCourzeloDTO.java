package com.example.evaluation.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCourzeloDTO {
    private Long id; // ou String, selon votre configuration
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
