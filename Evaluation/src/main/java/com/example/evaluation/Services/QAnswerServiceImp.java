package com.example.evaluation.Services;

import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Repositories.QAnswerRepo;
import com.example.evaluation.Repositories.QuestionTestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class QAnswerServiceImp implements IQAnswerService{
    private QuestionTestRepo questionTestRepo;
    private QAnswerRepo qAnswerRepo;
    @Override
    public List<QAnswer> retrieveAllQAnswers() {
        return qAnswerRepo.findAll();
    }

    @Override
    public QAnswer addQAnswer(QAnswer qAnswer) {
        return qAnswerRepo.save(qAnswer);
    }

    @Override
    public QAnswer updateQAnswer(QAnswer qAnswer) {
        return qAnswerRepo.save(qAnswer);
    }

    @Override
    public QAnswer retrieveQAnswer(String idQAnswer) {
        return qAnswerRepo.findById(idQAnswer).orElseThrow();
    }

    @Override
    public void removeQAnswer(String idQAnswer) {
        qAnswerRepo.deleteById(idQAnswer);
    }



    }
