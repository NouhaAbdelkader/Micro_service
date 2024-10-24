package com.example.forum.repositories;

import com.example.forum.entities.Answer;
import com.example.forum.entities.Votes;
import org.springframework.data.mongodb.repository.MongoRepository;

//import tn.esprit.courzelo.entities.UserCorzelo.UserCourzelo;

import java.util.List;

public interface VoteRepo  extends MongoRepository<Votes,String> {
    Votes findVotesById(String id);
   // Votes findVotesByTeacherAndAnswer(UserCourzelo u, Answer a);
    List<Votes> findVotesByAnswer(Answer a);
    void deleteAll(Iterable<? extends Votes> votes);
    Votes findVotesByTeacherIdAndAnswer(String teacherId, Answer a);

}
