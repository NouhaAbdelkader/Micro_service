package com.example.forum.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@Service

@AllArgsConstructor
public class RabbitMqListener {
    private final QuestionForumImpl questionForum;
    private final AnswerServiceImpl answerService;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "delete-questions-queue")
    public void deleteQuestionsForUser(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(message, Map.class);
            String userId = map.get("userId");

            questionForum.deleteQuestionsByUserId(userId);
            System.out.println("Questions deleted for user: " + userId);
        } catch (Exception e) {
            logger.error("Error deleting questions for user", e);
        }
    }
    @RabbitListener(queues = "delete-questions-queue-by-module")
    public void deleteQuestionsForModule(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(message, Map.class);
            String moduleId = map.get("moduleId");

            questionForum.deleteQuestionsByModuleId(moduleId);
            System.out.println("Questions deleted for module: " + moduleId);
        } catch (Exception e) {
            logger.error("Error deleting questions for module", e);
        }
    }

    @RabbitListener(queues = "delete-answers-queue")
    public void deleteAnswersForUser(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(message, Map.class);
            String userId = map.get("userId");

            answerService.deleteAnswersByUserId(userId);
            System.out.println("answers deleted for user: " + userId);
        } catch (Exception e) {
            logger.error("Error deleting answers for user", e);
        }
    }

}
