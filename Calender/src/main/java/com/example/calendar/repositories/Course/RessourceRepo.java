package com.example.calendar.repositories.Course;

import com.example.calendar.entities.Course.Ressource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourceRepo extends MongoRepository<Ressource, String> {
    List<Ressource> findByChapters(String chapterId);

}
