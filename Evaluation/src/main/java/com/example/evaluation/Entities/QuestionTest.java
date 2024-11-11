package com.example.evaluation.Entities;

import com.example.evaluation.Entities.QAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "QuestionTest")
public class QuestionTest {
    @Id
    private String id;

    @Indexed
    private String description;
    private  String correctAnswer;
    @DBRef
    private Set<QAnswer> answers;


}
