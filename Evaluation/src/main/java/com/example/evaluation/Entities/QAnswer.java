package com.example.evaluation.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Qanswer")
public class QAnswer {
    @Id
    private String id;
    @Indexed
    private String name;
    @Indexed
    private String description;

}
