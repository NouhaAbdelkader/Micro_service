package com.example.calendar.entities.Course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="ressources")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ressource {
    @Id
    private String ressourceId;
    private String titleRessource;
    private String descriptionRessource;
    private TypeRessource typeRessource;
    private String fileUrl; // Ajouter ce champ pour l'URL du fichier
    private String contentType; // MIME type of the file, e.g., "image/jpeg"
    private long fileSize; // Size of the file in bytes
    private String iconUrl; // URL or path to an icon representing the file type
    @DBRef
    @JsonBackReference
    private Chapter chapters;

}
