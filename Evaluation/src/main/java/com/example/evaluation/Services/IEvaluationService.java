package com.example.evaluation.Services;



import com.example.evaluation.Entities.Evaluation;
import com.example.evaluation.Entities.Module;
import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;

import java.util.List;
import java.util.Map;

public interface IEvaluationService {
    List<Evaluation> retrieveAllEvaluations();

    Evaluation  addEvaluation(String moduleId , String studentId , Evaluation  Evaluation);

    Evaluation updateEvaluation(Evaluation Evaluation);

    Evaluation retrieveEvaluation(String idEvaluation);
    void removeEvaluation (String idEvaluation);

    Evaluation ModuleEvaluation(String moduleId , String studentId  ) ;
    Evaluation assignStudentAndModuleToEvaluation(String evaluationId, String studentId , String ModuleId);
    List<Evaluation> retrieveEvaluationByUser( String studentId);

   Evaluation finalEvaluation(String studentId);
  List<String > analysePerformanceStrengths(String studentId);
    List<String > analysePerformanceweakneses(String studentId);


}
