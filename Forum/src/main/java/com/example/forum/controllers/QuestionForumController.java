package com.example.forum.controllers;

import com.example.forum.entities.QuestionForum;
import com.example.forum.entities.RateQuestion;
import com.example.forum.services.QuestionForumImpl;
import com.example.forum.services.RateQuestionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionForumController {
    QuestionForumImpl questionForumImpll;
    RateQuestionServiceImpl rateQuestionServiceImpl;

    // Méthode POST pour créer une question avec l'ID utilisateur en paramètre
    //@PostMapping("/create/{userId}/{moduleId}")
    @PostMapping(
            value = "/Teacher/create/{userId}/{moduleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public  ResponseEntity<QuestionForum >createQuestion(@RequestBody QuestionForum q, @PathVariable String userId, @PathVariable String moduleId, Authentication authentication) throws InterruptedException {
        Thread.sleep(1000);

        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            List<String> roles = (List<String>) realmAccess.get("roles");

            if (roles.contains("admin")) {
                return new ResponseEntity<>(questionForumImpll.AddQuestion(q, userId,moduleId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
