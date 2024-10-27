package com.example.calendar.repositories.classe;

import com.example.calendar.entities.Classe.PresenceGrp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceGrpRepository extends MongoRepository<PresenceGrp, String> {
}
