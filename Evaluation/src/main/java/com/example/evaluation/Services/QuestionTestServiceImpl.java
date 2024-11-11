package com.example.evaluation.Services;

import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Entities.QuestionTest;
import com.example.evaluation.Repositories.QAnswerRepo;
import com.example.evaluation.Repositories.QuestionTestRepo;
import com.example.evaluation.Repositories.TestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class QuestionTestServiceImpl implements IQuestionTestService {
private TestRepo testRepo;
private QuestionTestRepo questionTestRepo;
private QAnswerRepo qAnswerRepo;
    @Override
    public List<QuestionTest> retrieveAllQuestionTests() {
        return questionTestRepo.findAll();
    }

    @Override
    public QuestionTest addQuestionTest(QuestionTest questionTest) {
        return questionTestRepo.save(questionTest);
    }

    @Override
    public QuestionTest updateQuestionTest(QuestionTest questionTest) {
        return questionTestRepo.save(questionTest);
    }

    @Override
    public QuestionTest retrieveQuestionTest(String idQuestionTest) {
        return questionTestRepo.findById(idQuestionTest).orElseThrow();
    }

    @Override
    public void removeQuestionTest(String idQuestionTest) {
        questionTestRepo.deleteById(idQuestionTest);
    }



    @Override
    public QuestionTest AssignQuestionToAnswer(String idQuestionTest, List<QAnswer> answers) {
        QuestionTest questionTest =questionTestRepo.findById(idQuestionTest).orElseThrow();
        List<QAnswer> savedAnswers = qAnswerRepo.saveAll(answers);
        Set<QAnswer> existingAnswers = questionTest.getAnswers() != null ? questionTest.getAnswers() : new HashSet<>();
        existingAnswers.addAll(savedAnswers);
        questionTest.setAnswers(existingAnswers);
        return questionTestRepo.save(questionTest);
    }


}
