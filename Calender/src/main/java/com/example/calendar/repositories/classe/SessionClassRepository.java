package com.example.calendar.repositories.classe;
import com.example.calendar.entities.Classe.Classe;
import com.example.calendar.entities.Classe.SessionClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionClassRepository extends MongoRepository<SessionClass,String> {
    List<SessionClass> findByaClass(Classe cls);

}
