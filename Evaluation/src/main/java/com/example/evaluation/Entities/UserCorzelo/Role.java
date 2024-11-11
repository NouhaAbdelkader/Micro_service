package com.example.evaluation.Entities.UserCorzelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    @Indexed
    private ERole name;

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name=" + name +
                '}';
    }
}
