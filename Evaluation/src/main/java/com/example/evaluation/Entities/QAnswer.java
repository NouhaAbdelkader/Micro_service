package com.example.evaluation.Entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Qanswer")
public class QAnswer {
    @Id
    private String id;
    private String name;
    private String description;

}
