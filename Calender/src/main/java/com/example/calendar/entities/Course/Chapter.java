package com.example.calendar.entities.Course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection ="chapters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chapter {
    @Id
    private String chapterId;
    private String titleChapter;
    private String descriptionChapter;
    private Boolean isPublished;
    @DBRef
    @JsonManagedReference
    private List<Ressource> ressources;
    @DBRef
    private List<Activity> activities;
    @DBRef
    private Subject subject;
  /*  @DBRef
    private List<Assessment> assessments;*/
}
