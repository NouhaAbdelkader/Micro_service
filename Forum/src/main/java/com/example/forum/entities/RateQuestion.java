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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "RateQuestion")

public class RateQuestion {
    @Id
    private String id;
    @Indexed
    private int rateNumber ;
    @Indexed
    @DBRef
    private QuestionForum questionForum;

    private String userId;
}
