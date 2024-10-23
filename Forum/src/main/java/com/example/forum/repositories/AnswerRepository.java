package com.example.forum.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.forum.entities.Answer;
import com.example.forum.entities.QuestionForum;

import java.util.List;



public interface AnswerRepository extends MongoRepository<Answer, String> {
    // Ajoutez des méthodes personnalisées si nécessaire
    Answer findAnswerById(String Id);


    List<Answer> findAnswerByQuestionForumOrderByDateDesc(QuestionForum q);

    List<Answer> findAnswerByOrderByDateAsc() ;
    List<Answer> findAnswersByQuestionForumOrderByNbrVoteDesc(QuestionForum questionForum);




    //List<Answer> findByQuestionForumOrderByNbr_voteDesc(QuestionForum f);
}

