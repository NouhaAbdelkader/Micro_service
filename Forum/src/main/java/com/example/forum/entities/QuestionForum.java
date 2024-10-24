package com.example.forum.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
//import tn.esprit.courzelo.entities.UserCorzelo.UserCourzelo;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "QuestionForum")
@JsonIgnoreProperties({ "answers","rates"})
public class QuestionForum {
    @Id
    private String id;
    @Indexed
    private String title;
    @Indexed
    private String description;
    @Indexed
    private Date date ;
    @Indexed
    private int totalNbRate ;
    //private UserDto student;
    private String studentId;
    private String moduleId;

    @DBRef
    private List<Answer> answers;
    @DBRef
    private List<RateQuestion> rates;
}
