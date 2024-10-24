package com.example.forum.controllers;

import com.example.forum.entities.QuestionForum;
import com.example.forum.entities.RateQuestion;
import com.example.forum.services.QuestionForumImpl;
import com.example.forum.services.RateQuestionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionForumController {
    QuestionForumImpl questionForumImpll;
    RateQuestionServiceImpl rateQuestionServiceImpl;

    // Méthode POST pour créer une question avec l'ID utilisateur en paramètre
    @PostMapping("/create/{userId}/{moduleId}")
    public QuestionForum createQuestion(@RequestBody QuestionForum q, @PathVariable String userId,@PathVariable String moduleId) throws InterruptedException {
        Thread.sleep(1000);
        return questionForumImpll.AddQuestion(q, userId,moduleId);
    }
    @GetMapping("/questionsbyUser/{userId}")
    public List<QuestionForum> getQuestionsByUserId(@PathVariable String userId) {
        return questionForumImpll.getQuestionsByUserId(userId);
    }

    @GetMapping("/all")
    public List<QuestionForum> getAllQuestion() {
        return questionForumImpll.getAllQuestions();

    }
    @PutMapping("/update")
    public QuestionForum updateQuestion(@RequestBody QuestionForum q) {
        return  questionForumImpll.updateQuestion(q);

    }
    @GetMapping("/{id}")
    public QuestionForum getQuestionById(@PathVariable String id) {
        return questionForumImpll.getQuestionById(id);

    }

    @DeleteMapping("/{id}")
    public Void deleteQuestionById(@PathVariable String id) {
        questionForumImpll.deleteQuestionById(id);

        return null;
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteQuestionsByUserId(@PathVariable String userId) {
        questionForumImpll.deleteQuestionsByUserId(userId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/updateRateQustion/update")
    public QuestionForum updateQuestionRate(@RequestBody QuestionForum q) {
        return questionForumImpll.updateQuestionRate(q);
    }
    @GetMapping("/searchBytitle/{title}")
    public List<QuestionForum> getQuestionbytitle( @PathVariable String title) {
        return questionForumImpll.getQuestionByTitle(title);
    }
    @GetMapping("/searchBytitle/{moduleId}")
    public List<QuestionForum> getQuestionbymoduleId( @PathVariable String moduleId) {
        return questionForumImpll.getQuestionByModule(moduleId);
    }
    @PostMapping("/Rating/create/{questionId}/{userId}")
    public RateQuestion rateQuestion(@RequestBody RateQuestion rate, @PathVariable String questionId, @PathVariable String userId) {

        return rateQuestionServiceImpl.rateQuestion(rate, questionId, userId);

    }
    @GetMapping("/Rate/{id}")
    public RateQuestion getRateQuestionById(String id) {
        return rateQuestionServiceImpl.getRateQuestionById(id);
    }
    @PutMapping("/Rate/update")
    public RateQuestion updateRateQuestion(@RequestBody RateQuestion rate) {
        return rateQuestionServiceImpl.updateRateQuestion(rate);
    }

    @GetMapping("/Rate/{idQ}/{idU}")
    public RateQuestion getRateQuestionByQuestionAndUser(@PathVariable String idQ, @PathVariable String idU) {
        return rateQuestionServiceImpl.getRateQuestionByQuestionAndUser(idQ, idU);
    }

    @GetMapping("/getTotalRateAverage/{idQ}/")
    public float getTotalRte(@PathVariable String idQ) {
        return rateQuestionServiceImpl.avargeRateForQuestion(idQ);
    }
    @GetMapping("/orderByRatingAverge")
    public List<QuestionForum> getQuestionOrderByRatingAverge() {
        return rateQuestionServiceImpl.getQuestionOrderByRate();
    }
}
