package com.example.forum.controllers;


import com.example.forum.entities.Answer;
import com.example.forum.services.AnswerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/answers")
public class AnswerController {
    AnswerServiceImpl answerService ;
    @PostMapping("/create/{userId}/{questionId}")
    public Answer createAnswer(@RequestBody Answer answer, @PathVariable String userId, @PathVariable String questionId) {
        return  answerService.createAnswer(answer,userId, questionId);

    }


    @GetMapping("/answersbyUser/{userId}")
    public List<Answer> getAnswerByUserId(@PathVariable String userId) {
        return answerService.getAnswerByUserId(userId);
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAnswerByUserId(@PathVariable String userId) {
        answerService.deleteAnswersByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public Answer updateAnswer(@RequestBody Answer answer) {
        return  answerService.updateAnswer(answer);

    }

    @GetMapping("/all")
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();

    }

    @GetMapping("/{id}")
    public Answer getAnswerById(@PathVariable String id) {
        return answerService.getAnswerById(id);

    }
    @GetMapping("/nombreVote/{id}")
    public int getAnswerNbVote(@PathVariable String id) {
        return answerService.getNombreVoteAnswer(id);

    }

    @DeleteMapping("/{id}")
    public Void  deleteAnswer(@PathVariable String id) {
        answerService.deleteAnswer(id);

        return null;
    }
    @GetMapping("/getAnswersByQuestion/{idQuestion}")
    public List<Answer>  getQuestionById(@PathVariable String idQuestion) {
        return answerService.getAllAnswersByQuestion(idQuestion);
    }



    @GetMapping("/getOderByVote/{idQuestion}")
    public List<Answer> getAllAnswersOrderBuVote(@PathVariable String idQuestion) {
        return answerService.getAnswersOrderByNbVote(idQuestion);
    }
}
