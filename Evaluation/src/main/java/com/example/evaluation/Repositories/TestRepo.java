package com.example.evaluation.Repositories;


import com.example.evaluation.Entities.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepo extends MongoRepository<Test, String> {
}
