package com.example.calendar.entities.Course;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection ="userInteractions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserInteraction {
    @Id
    private String id;
    private String userId;
    private String subjectId;
    private String chapterId;
    private String resourceId;
    private String interactionType; // Par exemple, "visit", "download"
    private Date timestamp;
}
