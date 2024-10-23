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
    QuestionForumImpl questionForum;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "delete-questions-queue")
    public void deleteQuestionsForUser(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(message, Map.class);
            String userId = map.get("userId");
            logger.info("******* userId", userId);
            System.out.println("******* userId"+ userId);
            questionForum.deleteQuestionsByUserId(userId);
            System.out.println("Questions deleted for user: " + userId);
        } catch (Exception e) {
            logger.error("Error deleting questions for user", e);
        }
    }

}
