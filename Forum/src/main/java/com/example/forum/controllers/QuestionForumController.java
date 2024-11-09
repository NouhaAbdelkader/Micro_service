package com.example.forum.controllers;

import com.example.forum.entities.QuestionForum;
import com.example.forum.entities.RateQuestion;
import com.example.forum.services.QuestionForumImpl;
import com.example.forum.services.RateQuestionServiceImpl;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/forum/questions")
public class QuestionForumController {
    QuestionForumImpl questionForumImpll;
    RateQuestionServiceImpl rateQuestionServiceImpl;


    @PostMapping("/Student/create/{userId}/{moduleId}")
    @PreAuthorize("hasAuthority('Student')")
   // @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public QuestionForum createQuestion(@RequestBody QuestionForum q, @PathVariable String userId,@PathVariable String moduleId) throws InterruptedException {
        Thread.sleep(1000);
        return questionForumImpll.AddQuestion(q, userId,moduleId);
    }
    @GetMapping("/shared/questionsbyUser/{userId}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<QuestionForum> getQuestionsByUserId(@PathVariable String userId) {
        return questionForumImpll.getQuestionsByUserId(userId);
    }


    @GetMapping("/shared/all")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public ResponseEntity<List<QuestionForum>> getAllQuestion() {
        return ResponseEntity.ok(questionForumImpll.getAllQuestions());
    }

   @DeleteMapping(value = "/Student/del/{id}")
   @PreAuthorize("hasAuthority('Student')")
   public ResponseEntity<String> delete(@PathVariable("id") String id) {
       try {
           questionForumImpll.deleteQuestionById(id);
           return ResponseEntity.ok("Question with ID " + id + " has been successfully deleted.");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error deleting question: " + e.getMessage());
       }
   }
   @PutMapping("/Student/update")
    @PreAuthorize("hasAuthority('Student')")
    public QuestionForum updateQuestion(@RequestBody QuestionForum q) {
        return  questionForumImpll.updateQuestion(q);

    }
    @GetMapping("/shared/{id}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public QuestionForum getQuestionById(@PathVariable String id) {
        return questionForumImpll.getQuestionById(id);

    }


    @DeleteMapping("/Student/user/{userId}")
    @PreAuthorize("hasAuthority('Student')")
    public ResponseEntity<Void> deleteQuestionsByUserId(@PathVariable String userId) {
        questionForumImpll.deleteQuestionsByUserId(userId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/shared/updateRateQustion/update")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public QuestionForum updateQuestionRate(@RequestBody QuestionForum q) {
        return questionForumImpll.updateQuestionRate(q);
    }
    @GetMapping("/shared/searchBytitle/{title}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<QuestionForum> getQuestionbytitle( @PathVariable String title) {
        return questionForumImpll.getQuestionByTitle(title);
    }
    @GetMapping("/shared/searchBytitle/{moduleId}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<QuestionForum> getQuestionbymoduleId( @PathVariable String moduleId) {
        return questionForumImpll.getQuestionByModule(moduleId);
    }
    @PostMapping("/shared/Rating/create/{questionId}/{userId}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public RateQuestion rateQuestion(@RequestBody RateQuestion rate, @PathVariable String questionId, @PathVariable String userId) {

        return rateQuestionServiceImpl.rateQuestion(rate, questionId, userId);

    }
    @GetMapping("/shared/Rate/{id}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public RateQuestion getRateQuestionById(String id) {
        return rateQuestionServiceImpl.getRateQuestionById(id);
    }
    @PutMapping("/shared/Rate/update")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public RateQuestion updateRateQuestion(@RequestBody RateQuestion rate) {
        return rateQuestionServiceImpl.updateRateQuestion(rate);
    }

    @GetMapping("/shared/Rate/{idQ}/{idU}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public RateQuestion getRateQuestionByQuestionAndUser(@PathVariable String idQ, @PathVariable String idU) {
        return rateQuestionServiceImpl.getRateQuestionByQuestionAndUser(idQ, idU);
    }

    @GetMapping("/shared/getTotalRateAverage/{idQ}/")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public float getTotalRte(@PathVariable String idQ) {
        return rateQuestionServiceImpl.avargeRateForQuestion(idQ);
    }
    @GetMapping("/shared/orderByRatingAverge")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<QuestionForum> getQuestionOrderByRatingAverge() {
        return rateQuestionServiceImpl.getQuestionOrderByRate();
    }
}
