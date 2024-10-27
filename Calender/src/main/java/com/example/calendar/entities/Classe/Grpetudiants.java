package com.example.calendar.entities.Classe;

import com.example.calendar.entities.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Document(collection ="Grpetudiants")

public class Grpetudiants {

    @Id
    String groupId ;
    String NameGroup;
    Integer NbStudent;
    @DBRef
    private List<User> students = new ArrayList<>();
    @JsonIgnore
    @DBRef
    private List<PresenceGrp> presenceGrps = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private Classe classe;

}
