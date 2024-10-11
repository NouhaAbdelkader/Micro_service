package com.example.evaluation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QuestionTest")
public class QuestionTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private String description;
    private  String correctAnswer;
    @OneToMany(mappedBy = "QuestionTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QAnswer> answers;


}
