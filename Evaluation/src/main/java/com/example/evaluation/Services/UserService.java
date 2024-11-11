package com.example.evaluation.Services;

import com.example.evaluation.Entities.UserCorzelo.UserCourzelo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private final String userServiceUrl = "http://localhost:3000/users"; // Remplacez par l'URL de votre service User

    public UserCourzelo getUserById(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = userServiceUrl + "/" + userId; // API de récupération de l'utilisateur par ID
        return restTemplate.getForObject(url, UserCourzelo.class); // Retourne l'objet UserCourzelo
    }
}