package com.example.calendar.repositories.Course;

import com.example.calendar.entities.Course.UserInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInteractionRepo extends MongoRepository<UserInteraction, String> {
    // Define query methods here, if necessary
}
