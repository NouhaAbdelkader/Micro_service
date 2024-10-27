package com.example.calendar.entities.Classe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Document(collection ="session")


public class SessionClass {

    @Id
    String idSession;
    TypeSession typeSession;
    LocalDate sessionDate;
    String linkLiveS;
    LocalTime sessionStartHour;
    LocalTime sessionEndHour;
    @JsonIgnore
    @DBRef
    private Classe aClass;
    @JsonIgnore
    @DBRef
    private List<Presence> presences = new ArrayList<>();
    @JsonIgnore
    @DBRef
    private List<PresenceGrp> presenceGrps = new ArrayList<>();

}
