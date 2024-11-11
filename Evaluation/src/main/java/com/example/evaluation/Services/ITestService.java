package com.example.evaluation.Services;

import com.example.evaluation.Entities.Test;


import java.util.List;

public interface ITestService {
    List<Test> retrieveAllTests();

    Test addTest(String moduleId , String teacherId , Test TestQuizFinal);

    Test updateTest(Test TestQuizFinal);

    Test retrieveTest(String idTest);

    void removeTest (String idTest);

}
