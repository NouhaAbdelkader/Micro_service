package com.example.calendar.repositories.Course;

import com.example.calendar.entities.Course.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends MongoRepository<Activity,String> {
    @Query("{ 'subject.$id' : ?0 }")
    List<Activity> findBySubjectId(String subjectId);

}
