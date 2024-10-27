package com.example.calendar.entities.Classe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Document(collection ="PresenceGrp")

public class PresenceGrp {


    @Id
    String presenceId ;

    @CreatedDate
    private LocalDate datePresence;
    Integer NbPresent;
    Integer NbAbsent;


    @JsonIgnore
    @DBRef
    private Grpetudiants grpetudiants;
    @JsonIgnore
    @DBRef
    private SessionClass sessionClass;
}
