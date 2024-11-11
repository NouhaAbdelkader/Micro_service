package com.example.evaluation.Repositories;




import com.example.evaluation.Entities.Evaluation;
import com.example.evaluation.Entities.EvaluationType;
import com.example.evaluation.Entities.Module;
import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EvaluationRepo extends MongoRepository<Evaluation, String> {
    Evaluation findEvaluationByModuleAndAndStudent(Module module , UserCourzelo student);
  List <Evaluation> findByStudent(UserCourzelo student) ;
    List <Evaluation>   findAllByStudentAndEvaluationType(UserCourzelo student ,EvaluationType type);
    Evaluation findByStudentAndEvaluationType(UserCourzelo student , EvaluationType type);
}