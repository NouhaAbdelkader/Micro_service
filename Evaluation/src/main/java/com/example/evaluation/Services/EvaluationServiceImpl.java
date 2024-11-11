package com.example.evaluation.Services;

import com.example.evaluation.Entities.*;
import com.example.evaluation.Entities.Module;
import com.example.evaluation.Entities.UserCorzelo.Role;
import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import com.example.evaluation.Entities.UserCorzelo.badgeType;
import com.example.evaluation.Repositories.EvaluationRepo;
import com.example.evaluation.Repositories.TestRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.*;

import static com.example.evaluation.Entities.EvaluationType.FinalEvaluation;
import static com.example.evaluation.Entities.EvaluationType.ModuleEvaluation;


@AllArgsConstructor
@Service
public class EvaluationServiceImpl implements IEvaluationService {
    private TestRepo testRepo;
    private final WebClient webClient;
    private EvaluationRepo evaluationRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Override
    public List<Evaluation> retrieveAllEvaluations() {
        return evaluationRepo.findAll();
    }

    // Exemple de gestion des erreurs plus propre dans les appels WebClient
    public Mono<Module> getModule(String moduleId) {
        String url = "http://localhost:4005/api/modules/" + moduleId; // Assurez-vous que l'URL complète est valide
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("Erreur lors de la récupération du module : " + response.statusCode()));
                })
                .bodyToMono(Module.class)
                .onErrorMap(e -> new RuntimeException("Erreur lors de la récupération du module", e));
    }


    public Mono<UserCourzelo> getUser(String studentId) {
        // Assurez-vous que l'URL complète est valide
        String url = "http://localhost:4000/api/auth/" + studentId; // Remplacez par l'URL complète avec le port et le chemin

        // Log pour débogage
        System.out.println("URL construite : " + url);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("Erreur lors de la récupération de l'utilisateur : " + response.statusCode()));
                })
                .bodyToMono(UserCourzelo.class)
                .onErrorMap(e -> new RuntimeException("Erreur lors de la récupération de l'utilisateur", e));
    }

    // Simplification du code d'ajout d'évaluation

    @Override
    public Evaluation addEvaluation(String moduleId, String studentId, Evaluation evaluation) {
        Module module = getModule(moduleId).block(); // Vous pouvez utiliser block() ici si c'est absolument nécessaire, mais ce n'est pas recommandé pour une approche réactive
        UserCourzelo student = getUser(studentId).block();

        if (module == null || student == null) {
            throw new RuntimeException("Le module ou l'utilisateur n'a pas été trouvé.");
        }

        evaluation.setModule(module);
        evaluation.setStudent(student);
        return evaluationRepo.save(evaluation);
    }

    // Méthode pour récupérer l'utilisateur de manière réutilisable


    @Override
    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepo.save(evaluation);
    }

    @Override
    public Evaluation retrieveEvaluation(String idEvaluation) {
        return evaluationRepo.findById(idEvaluation).orElseThrow();
    }

    @Override
    public void removeEvaluation(String idEvaluation) {
        evaluationRepo.deleteById(idEvaluation);
    }

    @Override
    public Evaluation ModuleEvaluation(String moduleId, String studentId) {
        Module module = getModule(moduleId).block(); // Vous pouvez utiliser block() ici si c'est absolument nécessaire, mais ce n'est pas recommandé pour une approche réactive
        UserCourzelo student = getUser(studentId).block();

        Evaluation evaluation = evaluationRepo.findEvaluationByModuleAndAndStudent(module, student);
        int quizGrade = evaluation.getQuizGrade();
        int finalTestGrade = evaluation.getFinaltest_grade();
        double moduleAverage = 0;
        moduleAverage = finalTestGrade * 0.6 + quizGrade * 0.4;
        evaluation.setModuleAverage(moduleAverage);
        if (moduleAverage < 10) {
            evaluation.setComments("Unsatisfactory");
        } else if (moduleAverage == 10) {
            evaluation.setComments("Satisfactory");
        }
        if ((moduleAverage <= 15) & (moduleAverage > 10)) {
            evaluation.setComments("Commendable");
        }
        if ((moduleAverage > 15)) {
            evaluation.setComments("Outstanding");
        }
        return evaluationRepo.save(evaluation);

    }

    @Override
    public Evaluation assignStudentAndModuleToEvaluation(String evaluationId, String studentId, String moduleId) {
        Evaluation evaluation = evaluationRepo.findById(evaluationId).orElseThrow();
        Module module = getModule(moduleId).block(); // Vous pouvez utiliser block() ici si c'est absolument nécessaire, mais ce n'est pas recommandé pour une approche réactive
        UserCourzelo student = getUser(studentId).block();
        evaluation.setStudent(student);
        evaluation.setModule(module);
        return evaluationRepo.save(evaluation);
    }

    @Override
    public Evaluation finalEvaluation(String studentId) {
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(FinalEvaluation);
        UserCourzelo student = getUser(studentId).block();
        evaluation.setStudent(student);
        float coef ;
        float totcoeff=0 ;
        double moduleavg ;
        double finalav = 0;
        long q ;
        List<Evaluation> evaluations = retrieveEvaluationByUser(studentId);
        for(Evaluation ev : evaluations){
            coef = ev.getModule().getCoeff();
            moduleavg = ev.getModuleAverage();
            finalav += (coef*moduleavg);
            totcoeff += coef;
        }
        evaluation.setFinalAverage(finalav/totcoeff);
        if(evaluation.getFinalAverage()<10){
            evaluation.setHonors("Grade f");
        }
        else if(evaluation.getFinalAverage()==10){
            evaluation.setHonors("Grade D");
        }
        else if((evaluation.getFinalAverage()<=15) &(evaluation.getFinalAverage()>10)){
            evaluation.setHonors("Grade C");
        }
        else if((evaluation.getFinalAverage()>15)&(evaluation.getFinalAverage()<18)){
            evaluation.setHonors("Grade B");
        }
        else if(evaluation.getFinalAverage()>18){
            evaluation.setHonors("Grade A");
        }
        return evaluationRepo.save(evaluation);
    }


    @Override
    public List<String> analysePerformanceStrengths(String studentId) {
        List<Evaluation> evaluations= retrieveEvaluationByUser(studentId);
        List<String> strengths = new ArrayList<>();
        for(Evaluation e : evaluations){
            if(e.getModuleAverage()>15){
                strengths.add(e.getModule().getName());
            }
        }
        return strengths;
    }

    @Override
    public List<String>  analysePerformanceweakneses(String studentId) {
        UserCourzelo student = getUser(studentId).block();
        List<Evaluation> evaluations= retrieveEvaluationByUser(studentId);
        List<String> weakneses = new ArrayList<>();
        for(Evaluation e : evaluations){
            if(e.getModuleAverage()<10){
                weakneses.add(e.getModule().getName());
            }
        }
        return weakneses;
    }

    @Override
    public List<Evaluation> retrieveEvaluationByUser(String studentId) {
        UserCourzelo student = getUser(studentId).block();
        List<Evaluation> evaluations = evaluationRepo.findAllByStudentAndEvaluationType(student , ModuleEvaluation) ;
        return evaluations ;

    }


}