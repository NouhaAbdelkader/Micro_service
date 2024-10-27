package com.example.calendar.repositories.Course;

import com.example.calendar.entities.Course.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepo extends MongoRepository<Chapter, String> {
    @Query("{ 'subject.$id' : ?0 }")
    List<Chapter> findBySubjectId(String subjectId);
    // Dans votre ChapterRepository.java
    List<Chapter> findByIsPublishedTrue(String subjectId);

}
