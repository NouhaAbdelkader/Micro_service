package com.example.forum.services;

import com.example.forum.repositories.AnswerRepository;
import com.example.forum.repositories.QuestionForumRepo;
import com.example.forum.repositories.RateQuestionRepo;
import com.example.forum.repositories.VoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionForumImpl {
    QuestionForumRepo questionForumRepo ;
    AnswerRepository answerRepository;
    // ModuleRepo moduleRepo ;
    //UserRepository userRepository;
    RateQuestionRepo rateQuestionRepo;
    VoteRepo voteRepo;

}
