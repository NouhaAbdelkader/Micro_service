package com.example.evaluation.Entities;


import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "FinalQuizTest")
public class Test {
    @Id
    public  String id;
    @Indexed
    public Type type;
    @Indexed
    @NotBlank
    public String name;
    @Indexed
    public String description;
    @Indexed
    public String duration;
    @Indexed
    public Date date;
    @DBRef
    @NotBlank
    @Size(min = 1)
    public List<QuestionTest> questions;
    public Module module;
    public UserCourzelo student;
    public UserCourzelo teacher;
    @DBRef
    public List<Evaluation> evaluations;


}
