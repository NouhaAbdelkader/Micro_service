package com.example.forum.services;

import com.example.forum.entities.Answer;
import com.example.forum.entities.UserDto;
import com.example.forum.entities.Votes;
import com.example.forum.repositories.AnswerRepository;
import com.example.forum.repositories.VoteRepo;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class VoteServiceImpl {
    private  AnswerServiceImpl answerService ;
    private VoteRepo voteRepository;
    private AnswerRepository answerRepository;
    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);

    private void update_answer_nb_vote(int modifier, Votes vote) {
        int answer_vote = vote.getAnswer().getNbrVote();
        vote.getAnswer().setNbrVote( answer_vote + vote.getVoteType()* modifier);
        Answer updatedAnswer = answerService.updateVote(vote.getAnswer());

        if (updatedAnswer != null) {
            LOGGER.debug("Nombre de votes mis à jour pour la réponse : " + updatedAnswer);
        } else {
            LOGGER.debug("La mise à jour du nombre de votes pour la réponse a échoué.");
        }

    }

    public Votes add(Votes vote, String userId,String answerId) {

        String nodeUserUrl = "http://gestionUser:4000/api/auth/" + userId;  // Modifier l'URL en fonction de ton serveur Node.js
        UserDto u = restTemplate.getForObject(nodeUserUrl, UserDto.class);
        Answer a = answerRepository.findAnswerById(answerId);
        Date now= new Date();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(now);


        if (u != null && a != null) {
            vote.setTeacherId(userId);
            vote.setAnswer(a);
            vote.setDate(now);
            Votes savedVote = voteRepository.save(vote);
            if(savedVote!=null) {
                // Mettre à jour le nombre de votes de la réponse associée
                this.update_answer_nb_vote(1, savedVote);

                return savedVote;
            }

        }
        return null ;
    }
    public Votes update(Votes votes) {
        Votes vote = voteRepository.findVotesById(votes.getId());

        if (vote != null) {
            // Mettre à jour les attributs de l'entité Vote avec les valeurs de l'objet votes
            // Assurez-vous de vérifier si chaque attribut est différent avant de le mettre à jour
            vote.setVoteType(votes.getVoteType()); // Exemple d'attribut à mettre à jour

            Votes savedVote = voteRepository.save(vote); // Enregistrer les modifications dans la base de données
            if (savedVote != null) {
                // Mettre à jour le nombre de votes de la réponse associée
                this.update_answer_nb_vote(2, savedVote);
                return savedVote;
            }
            LOGGER.debug("La mise à jour du vote a échoué.");
        } else {
            LOGGER.debug("Vote non trouvé avec l'ID : " + votes.getId());
        }
        return null;
    }

    public void delete(String id) {
        Votes vote=voteRepository.findVotesById(id);


        voteRepository.delete(vote); // Supprimer le vote de la base de données
        update_answer_nb_vote(-1, vote); // Mettre à jour le nombre de votes associé

    }
    public Votes getVoteByUserAndAnswer(String userId, String answerId) {
        LOGGER.info("Début de la récupération du vote pour UserID: {} et AnswerID: {}", userId, answerId);

        // Vérification si l'ID utilisateur est null
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("L'ID utilisateur ne doit pas être null ou vide.");
        }

        // Récupération de l'entité Answer basée sur answerId
        Answer answer = answerRepository.findAnswerById(answerId);
        if (answer == null) {
            throw new IllegalArgumentException("Answer avec l'ID " + answerId + " introuvable.");
        }

        LOGGER.info("Answer trouvé : {}", answer);

        // Récupérer et retourner le Vote en utilisant l'ID utilisateur et l'entité Answer
        Votes vote = voteRepository.findVotesByTeacherIdAndAnswer(userId, answer);

        if (vote == null) {
            LOGGER.info("Aucun vote trouvé pour UserID: {} et AnswerID: {}", userId, answerId);
            throw new IllegalArgumentException("Aucun vote trouvé pour cet utilisateur et cette réponse.");
        }

        LOGGER.info("Vote trouvé : {}", vote);
        return vote;
    }





}
