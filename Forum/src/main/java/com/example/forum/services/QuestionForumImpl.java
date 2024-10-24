package com.example.forum.services;

import com.example.forum.entities.*;
import com.example.forum.repositories.AnswerRepository;
import com.example.forum.repositories.QuestionForumRepo;
import com.example.forum.repositories.RateQuestionRepo;
import com.example.forum.repositories.VoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionForumImpl {
    QuestionForumRepo questionForumRepo;
    AnswerRepository answerRepository;
    RateQuestionRepo rateQuestionRepo;
    VoteRepo voteRepo;

    private final RestTemplate restTemplate;


    public QuestionForum AddQuestion(QuestionForum questionForum, String userId, String moduleId) {
        // Récupérer les informations de l'utilisateur via Node.js (en utilisant l'ID)
        String nodeUserUrl = "http://localhost:4000/api/auth/" + userId;  // Modifier l'URL en fonction de ton serveur Node.js
        UserDto user = restTemplate.getForObject(nodeUserUrl, UserDto.class);
        String nodeModuleUrl = "http://localhost:4005/api/modules/" + moduleId;
        ModuleDto module = restTemplate.getForObject(nodeModuleUrl, ModuleDto.class);


        if (user != null && module != null) {
            // Si l'utilisateur est trouvé, on peut ajouter la question
            Date now = new Date();
            questionForum.setTotalNbRate(0);
            questionForum.setDate(now);
            questionForum.setStudentId(userId);
            questionForum.setModuleId(moduleId);

            // Tu peux éventuellement lier l'utilisateur à la question (ex: via un champ `author`)
            return questionForumRepo.save(questionForum);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    public List<QuestionForum> getQuestionsByUserId(String userId) {
        return questionForumRepo.findQuestionForumByStudentId(userId);
    }

    public List<QuestionForum> getAllQuestions() {
        return  questionForumRepo.findQuestionForumByOrderByDateDesc();
    }

    public QuestionForum getQuestionById(String id) {
        return questionForumRepo.findQuestionForumById(id);
    }

    // Update
    public QuestionForum updateQuestion(QuestionForum updatedQuestion) {
        QuestionForum existingQuestionOptional = questionForumRepo.findQuestionForumById(updatedQuestion.getId());

        // Vérifier si la question existe
        if (existingQuestionOptional!= null) {

            // Mettre à jour les attributs de la question existante avec les nouvelles valeurs
            existingQuestionOptional.setTitle(updatedQuestion.getTitle());
            existingQuestionOptional.setDescription(updatedQuestion.getDescription());
            existingQuestionOptional.setModuleId(updatedQuestion.getModuleId());

            // Sauvegarder les modifications dans la base de données
            return questionForumRepo.save(existingQuestionOptional);
        } else {

            return null ;
        }
    }

    // Delete
    public void deleteQuestionById(String id) {
        QuestionForum q =  questionForumRepo.findQuestionForumById(id);
        List<Answer> a=answerRepository.findAnswerByQuestionForumOrderByDateDesc(q);
        List<RateQuestion> qr= rateQuestionRepo.findRateQuestionByQuestionForum(q);



        //List<Votes> v = voteRepo.findVotesByAnswer()

        if (q != null) {

            if ( a!=null)  {
                for (Answer answer : a) {
                    List<Votes> v = voteRepo.findVotesByAnswer(answer);
                    if ( v!=null){
                        voteRepo.deleteAll(v);
                    }
                }
                // Utilisez une boucle for each pour itérer sur les votes
                answerRepository.deleteAll(a);

            }
            if ( qr!=null)  {
                // Utilisez une boucle for each pour itérer sur les votes
                rateQuestionRepo.deleteAll(qr);

            }
            // Supprimez la réponse une fois que les votes associés ont été supprimés
            questionForumRepo.delete(q);
        }

    }
    public void deleteQuestionsByUserId(String userId) {
        List<QuestionForum> questions = questionForumRepo.findQuestionForumByStudentId(userId);
        if (questions != null) {
            for (QuestionForum question : questions) {
                deleteQuestionById(question.getId());
            }
        }
    }
    public void deleteQuestionsByModuleId(String moduleId) {
        List<QuestionForum> questions = questionForumRepo.findQuestionForumByModuleId(moduleId);
        if (questions != null) {
            for (QuestionForum question : questions) {
                deleteQuestionById(question.getId());
            }
        }
    }
    public QuestionForum updateQuestionRate(QuestionForum updatedQuestion) {
        QuestionForum existingQuestionOptional = questionForumRepo.findQuestionForumById(updatedQuestion.getId());

        // Vérifier si la question existe
        if (existingQuestionOptional!= null) {

            existingQuestionOptional.setTotalNbRate(updatedQuestion.getTotalNbRate());

            // Sauvegarder les modifications dans la base de données
            return questionForumRepo.save(existingQuestionOptional);
        } else {

            return null ;
        }
    }

    public List<QuestionForum> getQuestionByTitle(String title){
        return questionForumRepo.findQuestionForumByTitleContaining(title);
    }

    public List<QuestionForum> getQuestionByModule(String moduleId){
        return questionForumRepo.findQuestionForumByModuleId(moduleId);
    }



}
