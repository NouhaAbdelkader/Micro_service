package com.example.calendar.repositories.classe;
import com.example.calendar.entities.Classe.Presence;
import com.example.calendar.entities.Classe.SessionClass;
import com.example.calendar.entities.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresenceRepository extends MongoRepository<Presence,String> {
    Optional<Presence> findByUserAndSessionClass(User user, SessionClass sessionClass);

}
