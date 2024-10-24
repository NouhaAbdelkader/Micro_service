package com.example.forum.services;

import com.example.forum.entities.Answer;
import com.example.forum.entities.QuestionForum;
import com.example.forum.entities.UserDto;
import com.example.forum.entities.Votes;
import com.example.forum.repositories.AnswerRepository;
import com.example.forum.repositories.QuestionForumRepo;
import com.example.forum.repositories.VoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl {
    AnswerRepository answerRepository ;
    QuestionForumRepo questionForumRepository ;
    VoteRepo voteRepo;
    private final RestTemplate restTemplate;

    public Answer createAnswer(Answer answer,String userId, String questionId) {
        QuestionForum question = questionForumRepository.findQuestionForumById(questionId);
        String nodeUserUrl = "http://localhost:4000/api/auth/" + userId;  // Modifier l'URL en fonction de ton serveur Node.js
        UserDto u = restTemplate.getForObject(nodeUserUrl, UserDto.class);
        Date now = new Date();
        if ( question !=null && u!= null) {

            answer.setQuestionForum( question );
            answer.setUserId(userId);
            answer.setDate(now);
            answer.setNbrVote(0);
            answer.setGetBudget(false);
            return answerRepository.save(answer);
        } else {
            // Gérer le cas où la question n'est pas trouvée
            return null;
        }
    }

    public List<Answer> getAnswerByUserId(String userId) {
        return answerRepository.findAnswersByUserId(userId);
    }
    public List<Answer> getAllAnswers() {
        return answerRepository.findAnswerByOrderByDateAsc();
    }
    public Answer getAnswerById(String id) {
        Answer s= answerRepository.findAnswerById(id);
        return s ;

    }
    public Answer updateAnswer(Answer a){
        return  answerRepository.save(a);

    }
    public void deleteAnswer(String answerId) {
        Answer answer = answerRepository.findAnswerById(answerId);
        List<Votes> votes= voteRepo.findVotesByAnswer(answer);

        if (answer != null) {

            if ( votes!=null) {
                // Utilisez une boucle for each pour itérer sur les votes
                voteRepo.deleteAll(votes);
            }
            // Supprimez la réponse une fois que les votes associés ont été supprimés
            answerRepository.delete(answer);
        }
    }

    public void deleteAnswersByUserId(String userId) {
        List<Answer> answers = answerRepository.findAnswersByUserId(userId);
        if (answers != null) {
            for (Answer a : answers) {
                deleteAnswer(a.getId());
            }
        }
    }
    public int getNombreVoteAnswer(String idAnswer){
        Answer a= answerRepository.findAnswerById(idAnswer);
        return a.getNbrVote();

    }
    public List<Answer> getAnswersOrderByNbVote(String s){
        QuestionForum q= questionForumRepository.findQuestionForumById(s);

        return answerRepository.findAnswersByQuestionForumOrderByNbrVoteDesc(q);
    }
    public  List<Answer> getAllAnswersByQuestion(String id){
        QuestionForum q= questionForumRepository.findQuestionForumById(id);
        return answerRepository.findAnswerByQuestionForumOrderByDateDesc(q);

    }
    public Answer updateVote(Answer answer) {

        return  answerRepository.save(answer);

    }
}
