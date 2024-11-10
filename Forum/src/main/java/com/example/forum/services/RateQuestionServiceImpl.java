package com.example.forum.services;

import com.example.forum.entities.QuestionForum;
import com.example.forum.entities.RateQuestion;
import com.example.forum.entities.UserDto;
import com.example.forum.repositories.QuestionForumRepo;
import com.example.forum.repositories.RateQuestionRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RateQuestionServiceImpl {
    RateQuestionRepo rateQuestionRepo;
    QuestionForumRepo questionForumRepo;
    QuestionForumImpl questionForumimpl ;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RateQuestionServiceImpl.class);

    public RateQuestion rateQuestion(RateQuestion rate, String questionId, String userId)  {
        String nodeUserUrl = "http://gestionUser:4000/api/auth/" + userId;  // Modifier l'URL en fonction de ton serveur Node.js
        UserDto u = restTemplate.getForObject(nodeUserUrl, UserDto.class);
        QuestionForum q= questionForumRepo.findQuestionForumById(questionId);
        if (u!= null && q!=null) {
            rate.setQuestionForum(q);
            rate.setUserId(userId);
            RateQuestion rated = rateQuestionRepo.save(rate);
            QuestionForum question = new QuestionForum();
            question.setId(q.getId());
            question.setTotalNbRate(q.getTotalNbRate()+rate.getRateNumber());
            questionForumimpl.updateQuestionRate(question);
            return  rated;

        }
        return null;
    }

    public RateQuestion getRateQuestionById(String id) {
        return rateQuestionRepo.findRateQuestionById(id);
    }

    public RateQuestion updateRateQuestion(RateQuestion rate) {
        RateQuestion existingRateQuestion = rateQuestionRepo.findRateQuestionById(rate.getId());
        if (existingRateQuestion!= null) {
            int oldRate = existingRateQuestion.getRateNumber();
            existingRateQuestion.setRateNumber(rate.getRateNumber());

            LOGGER.info("old rate *******************"+oldRate);
            existingRateQuestion.setRateNumber(rate.getRateNumber());
            QuestionForum q= existingRateQuestion.getQuestionForum();
            RateQuestion rated = rateQuestionRepo.save( existingRateQuestion);
            int ratenumber=q.getTotalNbRate()-oldRate;
            LOGGER.info("new rate number *******************"+ ratenumber);
            q.setTotalNbRate(ratenumber+rate.getRateNumber());
            questionForumimpl.updateQuestionRate(q);
            this.avargeRateForQuestion(q.getId());
            return rated;

        }
        return null;
    }
    public float avargeRateForQuestion(String idQ) {
        QuestionForum q= questionForumRepo.findQuestionForumById(idQ);
        List<RateQuestion> rates= rateQuestionRepo.findRateQuestionByQuestionForum(q);
        float sum = (float) q.getTotalNbRate() /rates.size();
        LOGGER.info("rates *******************"+sum);
        return (float) q.getTotalNbRate() /rates.size();


    }
    public RateQuestion getRateQuestionByQuestionAndUser(String idQ,String idu) {

        QuestionForum q= questionForumRepo.findQuestionForumById(idQ);
        return rateQuestionRepo.findRateQuestionByUserIdAndAndQuestionForum(idu, q);
    }
    public List<QuestionForum> getQuestionOrderByRate() {
        List<QuestionForum> q = questionForumRepo.findAll();
        Map<QuestionForum, Float> qRateMap = new HashMap<>();

        for (QuestionForum qu : q) {
            if (qu.getTotalNbRate()!= 0) {
                float avgRate = this.avargeRateForQuestion(qu.getId());
                qRateMap.put(qu, avgRate);
            }
        }

        // Trier la map par ordre décroissant des taux moyens
        Map<QuestionForum, Float> sortedMap = qRateMap.entrySet().stream()
                .sorted(Map.Entry.<QuestionForum, Float>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Créer une liste pour stocker les questions triées
        return new ArrayList<>(sortedMap.keySet());
    }

}
