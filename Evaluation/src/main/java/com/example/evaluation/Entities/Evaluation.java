package com.example.evaluation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluation") // Nom de la table dans MySQL
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment pour MySQL
    private Long id; // Utilisez Long pour les ID

    private int attendanceGrade; // Conventions de nommage Java : camelCase
    private int quizGrade;
    private int finalTestGrade;
    private String honors;
    private int rank;
    private int rankClass;
    private double moduleAverage;
    private double finalAverage;

    @Enumerated(EnumType.STRING) // Si EvaluationType est un enum
    private EvaluationType evaluationType;

    private String comments;
    private int rankModule;

    @OneToMany(mappedBy = "evaluation")
    private List<Test> tests;

    private Long studentId; // Stocke l'ID de l'étudiant
    // Vous pouvez garder le DTO ici pour récupérer les détails
    @Transient // Ne pas persister dans la base de données
    private UserCourzeloDTO student; // Utilisez le DTO pour afficher les détails de l'étudiant


   // @ManyToOne // Ajustez selon votre relation
   // private Module module;
}
