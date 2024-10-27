package com.example.calendar.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Cette annotation de Lombok génère les getters, setters, toString, equals et hashCode.
@NoArgsConstructor // Génère un constructeur sans argument.
@AllArgsConstructor // Génère un constructeur avec tous les arguments.
public class InteractionStat {
    private String id; // Peut représenter subjectId, chapterId, ou resourceId selon le contexte
    private long totalInteractions;
    private String subjectTitle; // Titre du sujet
    private double percentage; // Pourcentage des interactions
    private String chapterTitle; // Titre du chapitre
    private String RessourceTitle; // Titre de la ressource
}
