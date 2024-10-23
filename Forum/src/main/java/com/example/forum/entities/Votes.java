package com.example.forum.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
//import tn.esprit.courzelo.entities.UserCorzelo.UserCourzelo;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Votes")
public class Votes {
    @Id
    private String id;
    @Indexed
    private int voteType;
    @Indexed
    private Date date ;
    @DBRef
    @Indexed
    private Answer answer;
    /*@DBRef
    @Indexed
    private UserCourzelo teacher;*/
}
