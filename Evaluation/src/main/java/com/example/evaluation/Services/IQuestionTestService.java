package com.example.evaluation.Services;



import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Entities.QuestionTest;

import java.util.List;

public interface IQuestionTestService {
    List<QuestionTest> retrieveAllQuestionTests();

    QuestionTest  addQuestionTest(QuestionTest  QuestionTest);

    QuestionTest updateQuestionTest(QuestionTest QuestionTest);

    QuestionTest retrieveQuestionTest(String idQuestionTest);
    void removeQuestionTest (String idQuestionTest);

    QuestionTest AssignQuestionToAnswer(String idQuestionTest ,  List<QAnswer> answers);

}
