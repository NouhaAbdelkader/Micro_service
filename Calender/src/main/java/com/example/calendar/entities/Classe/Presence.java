package com.example.calendar.entities.Classe;

import com.example.calendar.entities.User.User;
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
@Document(collection ="Presence")
public class Presence {


    @Id
    String presenceId ;

    @CreatedDate
    private LocalDate datePresence;
    Boolean IsPresent;

    Boolean IsConfirmed;

    @JsonIgnore
    @DBRef
    private User user;

    @JsonIgnore
    @DBRef
    private SessionClass sessionClass;





}
