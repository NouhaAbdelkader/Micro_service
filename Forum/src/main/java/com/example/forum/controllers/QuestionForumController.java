package com.example.forum.controllers;

import com.example.forum.entities.QuestionForum;
import com.example.forum.services.QuestionForumImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionForumController {
    QuestionForumImpl questionForumImpll;

    // Méthode POST pour créer une question avec l'ID utilisateur en paramètre
    @PostMapping("/create/{userId}")
    public QuestionForum createQuestion(@RequestBody QuestionForum q, @PathVariable String userId) throws InterruptedException {
        Thread.sleep(1000);
        return questionForumImpll.AddQuestion(q, userId);
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

}
