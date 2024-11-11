package com.example.evaluation.Controllers.EvaluationController;


import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Services.IQAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/QAnswers")
public class QAnswerController {

    private IQAnswerService iqAnswerService;
    @PostMapping("/add")
    public QAnswer addQAnswer(@RequestBody QAnswer qAnswer){
        return  iqAnswerService.addQAnswer(qAnswer);
    }

    @GetMapping("/all")
    public List<QAnswer> getAllQAnswers(){
        return iqAnswerService.retrieveAllQAnswers();
    }

    @PutMapping("/update")
    public QAnswer updateQAnswer(@RequestBody QAnswer qAnswer){
        return  iqAnswerService.updateQAnswer(qAnswer);
    }

    @GetMapping("/get/{idQAnswer}")
    public QAnswer getById(@PathVariable("idQAnswer") String idQAnswer){
        return iqAnswerService.retrieveQAnswer(idQAnswer);
    }

    @DeleteMapping("/remove/{idQAnswer}")
    public ResponseEntity<String> removeQAnswer(@PathVariable("idQAnswer") String idQAnswer) {
        try {
            iqAnswerService.removeQAnswer(idQAnswer);
            return ResponseEntity.ok("QAnswer removed successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QAnswer not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the QAnswer");
        }
    }




    }







