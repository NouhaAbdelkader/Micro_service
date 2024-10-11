package com.example.evaluation.Entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "final_quiz_test") // Table name in MySQL
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String name;

    private String description;

    private String duration;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int mark;

    private int nbQst = 20;

    private int rank;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionTest> questions;

   // @ManyToOne
  //  @JoinColumn(name = "module_id")  // Foreign key to module table
  //  private Module module;

    @Transient
    private UserCourzeloDTO student;  // Remplacer par le DTO

    @Transient
    private UserCourzeloDTO teacher;  // Remplacer par le DTO

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;
}
