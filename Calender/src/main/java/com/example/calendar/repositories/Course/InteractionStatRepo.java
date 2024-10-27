package com.example.calendar.repositories.Course;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionStatRepo extends MongoRepository<InteractionStatRepo, String> {
}
