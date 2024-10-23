package com.example.forum.entities;



import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean canVote;
    private List<Votes> votes;
    private List<QuestionForum> questionForums;
    private List<Answer> answers;
    // Ajouter d'autres champs si nécessaire
}
