package com.example.calendar.entities.Course;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection ="activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Activity {
    @Id
    private String activityId;
    private String titleActivity;
    private Date startDate;
    private Date endDate;
    private Float NoteActivity;
    @DBRef
    private Chapter chapter;
    @DBRef
    private Subject subject;


}
