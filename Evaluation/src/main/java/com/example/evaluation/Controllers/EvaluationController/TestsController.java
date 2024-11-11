package com.example.evaluation.Controllers.EvaluationController;


import com.example.evaluation.Entities.QuestionTest;
import com.example.evaluation.Entities.Test;
import com.example.evaluation.Services.ITestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/Tests")
public class TestsController {
    private ITestService iTestService;
    @PostMapping("/add/{moduleId}/{teacherId}")
    public Test addTest(@PathVariable("moduleId") String moduleId , @PathVariable("teacherId") String teacherId , @RequestBody Test TestQuizFinal){
        return  iTestService.addTest(moduleId ,teacherId , TestQuizFinal);
    }

    @GetMapping("/all")
    public List<Test> getAllTests(){
        return iTestService.retrieveAllTests();
    }

    @PutMapping("/update")
    public Test updateTest(@RequestBody Test TestQuizFinal){
        return  iTestService.updateTest(TestQuizFinal);
    }

    @GetMapping("/get/{idTest}")
    public Test getById(@PathVariable("idTest") String idTest){
        return iTestService.retrieveTest(idTest);
    }
    @DeleteMapping("/remove/{idTest}")
    public ResponseEntity<Object> removeTest(@PathVariable("idTest") String idTest) {
        try {
            iTestService.removeTest(idTest);
            return ResponseEntity.ok().build(); // Return an empty JSON object
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the Test");
        }

    }


}
