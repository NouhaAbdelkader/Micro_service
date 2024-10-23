package com.example.forum.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.forum.entities.QuestionForum;

import java.util.List;

public interface QuestionForumRepo  extends MongoRepository<QuestionForum,String> {
    QuestionForum findQuestionForumById(String id);

    List<QuestionForum> findQuestionForumByOrderByDateDesc();
    List<QuestionForum> findQuestionForumByModule(Module module);
    List<QuestionForum> findQuestionForumByTitleContaining(String title);
    List<QuestionForum> findQuestionForumByStudentId(String userId);

}
