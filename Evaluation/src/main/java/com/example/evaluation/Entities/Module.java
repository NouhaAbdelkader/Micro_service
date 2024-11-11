package com.example.evaluation.Entities;

import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Module {
    private  String id;
    private String name;
    private String description;
    private int nbHeurePerWeek;
    private int nbHeureTotal;
    private String image;
    private Float coeff;
    private List<Class> classes;
 private List<Evaluation> evaluation;
 private List<Test> tests; // Assurez-vous que cette propriété existe
    private List<UserCourzelo> Teachers;

}
