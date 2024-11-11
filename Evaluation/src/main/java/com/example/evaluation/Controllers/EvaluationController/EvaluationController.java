package com.example.evaluation.Controllers.EvaluationController;

import com.example.evaluation.Entities.Evaluation;
import com.example.evaluation.Services.IEvaluationService;
import com.example.evaluation.Services.ITestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/evaluations")
public class EvaluationController {
    private IEvaluationService iEvaluationService;
    private ITestService iTestService;
    @PostMapping("/add/{moduleId}/{studentId}")
    public Evaluation addEvaluation(@PathVariable String moduleId , @PathVariable String studentId , @RequestBody Evaluation evaluation){
        return  iEvaluationService.addEvaluation(moduleId , studentId ,evaluation);
    }

    @GetMapping("/all")
    public List<Evaluation> getAllEvaluations(){
        return iEvaluationService.retrieveAllEvaluations();
    }

    @PutMapping("/update")
    public Evaluation updateEvaluation(@RequestBody Evaluation evaluation){
        return  iEvaluationService.updateEvaluation(evaluation);
    }

    @GetMapping("/get/{idEvaluation}")
    public Evaluation getById(@PathVariable("idEvaluation") String idEvaluation){
        return iEvaluationService.retrieveEvaluation(idEvaluation);
    }

    @DeleteMapping("/remove/{idEvaluation}")
    public ResponseEntity<String> removeEvaluation(@PathVariable("idEvaluation") String idEvaluation) {
        try {
            iEvaluationService.removeEvaluation(idEvaluation);
            return ResponseEntity.ok("Evaluation removed successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluation not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the evaluation");
        }
    }







    @PostMapping("/assignStudentAndModuleToEvaluation")
    public ResponseEntity<Evaluation> assignStudentToEvaluation(
            @RequestParam("evaluationId") String evaluationId,
            @RequestParam("studentId") String studentId,@RequestParam("moduleId") String moduleId
            ) {
        try {
            Evaluation updateEvaluation = iEvaluationService.assignStudentAndModuleToEvaluation(evaluationId, studentId , moduleId);
            return new ResponseEntity<>(updateEvaluation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/module-evaluation")
    public ResponseEntity<Evaluation> moduleEvaluation(
            @RequestParam ("moduleId") String moduleId,
            @RequestParam ("studentId") String studentId) {
        try {
            Evaluation result = iEvaluationService.ModuleEvaluation( moduleId, studentId);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/allbystudent/{studentId}")
    public List<Evaluation> getAllEvaluationsBystudent(@PathVariable String studentId){
        return iEvaluationService.retrieveEvaluationByUser(studentId);
    }


    @PostMapping("/final/{idStudent}")
    public Evaluation finalGrade(@PathVariable ("idStudent")String studentId) {
        Evaluation evaluation = iEvaluationService.finalEvaluation(studentId );
        return evaluation ;

    }

    @GetMapping("/strengths/{idStudent}")
    public List<String> strengths(@PathVariable ("idStudent")String studentId) {
        List<String> strengths= iEvaluationService.analysePerformanceStrengths(studentId);
        return strengths ;

    }
    @GetMapping("/weaknesses/{idStudent}")
    public List<String> weaknesses(@PathVariable ("idStudent")String studentId) {
        List<String> weaknesses= iEvaluationService.analysePerformanceweakneses(studentId);
        return weaknesses ;

    }


}


