package com.example.evaluation.Services;

import com.example.evaluation.Entities.Module;
import com.example.evaluation.Entities.QAnswer;
import com.example.evaluation.Entities.QuestionTest;
import com.example.evaluation.Entities.Test;
import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import com.example.evaluation.Repositories.QAnswerRepo;
import com.example.evaluation.Repositories.QuestionTestRepo;
import com.example.evaluation.Repositories.TestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestServiceImp implements ITestService {

    private final TestRepo testRepo;
    private final QuestionTestRepo questionTestRepo;
    private final QAnswerRepo qAnswerRepo;
    private final QAnswerServiceImp qAnswerServiceImp;
    private final QuestionTestServiceImpl questionTestService;
    private final WebClient webClient;

    @Override
    public List<Test> retrieveAllTests() {
        return testRepo.findAll();
    }


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


    @Override
    public Test addTest(String moduleId, String teacherId, Test testQuizFinal) {
        Module module = getModule(moduleId).block(); // Vous pouvez utiliser block() ici si c'est absolument nécessaire, mais ce n'est pas recommandé pour une approche réactive
        UserCourzelo teacher = getUser(teacherId).block();
        // Enregistrer les questions et réponses associées
        for (QuestionTest question : testQuizFinal.getQuestions()) {
            qAnswerRepo.saveAll(question.getAnswers());
        }
        questionTestRepo.saveAll(testQuizFinal.getQuestions());

        // Associer le professeur et le module au test
        testQuizFinal.setTeacher(teacher);
        testRepo.save(testQuizFinal);

        if (module.getTests() == null) {
            module.setTests(new ArrayList<>());
        }
        module.getTests().add(testQuizFinal);

        // Sauvegarder les informations du module avec le test ajouté
        // (en supposant une mise à jour via un autre microservice ou persistance directe dans votre contexte)

        return testQuizFinal;
    }

    @Override
    public Test updateTest(Test testQuizFinal) {
        for (QuestionTest question : testQuizFinal.getQuestions()) {
            for (QAnswer qAnswer : question.getAnswers()) {
                qAnswerServiceImp.updateQAnswer(qAnswer);
            }
            questionTestService.updateQuestionTest(question);
        }
        return testRepo.save(testQuizFinal);
    }

    @Override
    public Test retrieveTest(String idTest) {
        return testRepo.findById(idTest).orElseThrow(() -> new RuntimeException("Test non trouvé avec ID : " + idTest));
    }

    @Override
    public void removeTest(String idTest) {
        Test testQuizFinal = testRepo.findById(idTest).orElseThrow(() -> new RuntimeException("Test non trouvé avec ID : " + idTest));
        for (QuestionTest question : testQuizFinal.getQuestions()) {
            for (QAnswer qAnswer : question.getAnswers()) {
                qAnswerServiceImp.removeQAnswer(qAnswer.getId());
            }
            questionTestService.removeQuestionTest(question.getId());
        }
        testRepo.deleteById(idTest);
    }



}
