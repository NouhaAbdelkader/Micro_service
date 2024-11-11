package com.example.evaluation.Controllers.EvaluationController;


import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Entities.QuestionTest;
import com.example.evaluation.Services.IQuestionTestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/QuestionTests")
public class QuestionTestController {
    private IQuestionTestService iQuestionTestService;

    @PostMapping("/add")
    public QuestionTest addQuestionTest(@RequestBody QuestionTest questionTest){
        return  iQuestionTestService.addQuestionTest(questionTest);
    }

    @GetMapping("/all")
    public List<QuestionTest> getAllQuestionTests(){
        return iQuestionTestService.retrieveAllQuestionTests();
    }

    @PutMapping("/update")
    public QuestionTest updateQuestionTest(@RequestBody QuestionTest questionTest){
        return  iQuestionTestService.updateQuestionTest(questionTest);
    }

    @GetMapping("/get/{idQuestionTest}")
    public QuestionTest getById(@PathVariable("idQuestionTest") String idquestionTest){
        return iQuestionTestService.retrieveQuestionTest(idquestionTest);
    }

    @DeleteMapping("/remove/{idQuestionTest}")
    public ResponseEntity<String> removeQuestionTest(@PathVariable("idQuestionTest") String idquestionTest) {
        try {
            iQuestionTestService.removeQuestionTest(idquestionTest);
            return ResponseEntity.ok("QuestionTest removed successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QuestionTest not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the QuestionTest");
        }
    }

    @PostMapping("/addAndAsign/{idQuestionTest}")
    public ResponseEntity<QuestionTest> AndAssignQuestionToAnswers(
            @PathVariable ("idQuestionTest")String idquestionTest,
            @RequestBody List<QAnswer> answers) {

        QuestionTest questionTest1 = iQuestionTestService.AssignQuestionToAnswer(idquestionTest, answers);

        return new ResponseEntity<>(questionTest1, HttpStatus.CREATED);
    }


}
