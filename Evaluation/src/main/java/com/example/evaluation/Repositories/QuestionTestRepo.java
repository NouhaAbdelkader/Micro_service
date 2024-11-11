package com.example.evaluation.Repositories;


import com.example.evaluation.Entities.QuestionTest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionTestRepo extends MongoRepository<QuestionTest, String> {
}
