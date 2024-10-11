package com.example.evaluation.Services;
import com.example.evaluation.Entities.UserCourzeloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:4000/api/auth/";

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserCourzeloDTO getUserById(Long userId) {
        String url = USER_SERVICE_URL + userId; // URL de l'API pour obtenir l'utilisateur par ID
        return restTemplate.getForObject(url, UserCourzeloDTO.class); // Appel à l'API REST
    }
}
