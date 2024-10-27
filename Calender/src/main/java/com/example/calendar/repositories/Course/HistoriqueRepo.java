package com.example.calendar.repositories.Course;

import com.example.calendar.entities.Course.Historique;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueRepo extends MongoRepository<Historique, String> {
    @Query("{ $or: [ { 'titleRessource': { $regex: ?0, $options: 'i' } }, { 'descriptionRessource': { $regex: ?1, $options: 'i' } } ] }")
    List<Historique> findByTitleRessourceContainingIgnoreCaseOrDescriptionRessourceContainingIgnoreCase(String title, String description);

}
