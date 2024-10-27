package com.example.calendar.repositories.classe;

import com.example.calendar.entities.Classe.Grpetudiants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrpetudiantsRepository  extends MongoRepository<Grpetudiants,String> {


}
