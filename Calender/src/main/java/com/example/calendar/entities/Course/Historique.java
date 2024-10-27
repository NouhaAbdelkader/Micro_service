package com.example.calendar.entities.Course;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="historiques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Historique {
    @Id
    private String historiqueId;
    private String titleRessource;
    private String descriptionRessource;
    private TypeRessource typeRessource;
    private String fileUrl;
    private String contentType;
    private long fileSize;
    private String iconUrl;


}
