package com.example.evaluation.Repositories;


import com.example.evaluation.Entities.QAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QAnswerRepo extends MongoRepository<QAnswer, String> {
}
