package com.example.forum.controllers;


import com.example.forum.configurations.KeycloakConfig;
import com.example.forum.entities.Answer;
import com.example.forum.services.AnswerServiceImpl;
import com.example.forum.services.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/forum/answers")
public class AnswerController {
    AnswerServiceImpl answerService ;
    private final KeycloakConfig keycloakService;
    @PostMapping("/Student/create/{userId}/{questionId}")
    @PreAuthorize("hasAuthority('Student')")
    public Answer createAnswer(@RequestBody Answer answer, @PathVariable String userId, @PathVariable String questionId) {
        return  answerService.createAnswer(answer,userId, questionId);

    }
    @GetMapping("/keycloak/users")
    public List<String> getAllUsers() {
        return keycloakService.getAllUsers();
    }


    @GetMapping("/shared/answersbyUser/{userId}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<Answer> getAnswerByUserId(@PathVariable String userId) {
        return answerService.getAnswerByUserId(userId);
    }
    @DeleteMapping("/Student/user/{userId}")
    @PreAuthorize("hasAuthority('Student')")
    public ResponseEntity<Void> deleteAnswerByUserId(@PathVariable String userId) {
        answerService.deleteAnswersByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/Student/update")
    @PreAuthorize("hasAuthority('Student')")
    public Answer updateAnswer(@RequestBody Answer answer) {
        return  answerService.updateAnswer(answer);

    }

    @GetMapping("/shared/all")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();

    }

    @GetMapping("/shared/{id}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public Answer getAnswerById(@PathVariable String id) {
        return answerService.getAnswerById(id);

    }
    @GetMapping("/shared/nombreVote/{id}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public int getAnswerNbVote(@PathVariable String id) {
        return answerService.getNombreVoteAnswer(id);

    }

    @DeleteMapping("/Student/{id}")
    @PreAuthorize("hasAuthority('Student')")
    public Void  deleteAnswer(@PathVariable String id) {
        answerService.deleteAnswer(id);

        return null;
    }
    @GetMapping("/shared/getAnswersByQuestion/{idQuestion}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<Answer>  getQuestionById(@PathVariable String idQuestion) {
        return answerService.getAllAnswersByQuestion(idQuestion);
    }



    @GetMapping("/shared/getOderByVote/{idQuestion}")
    @PreAuthorize("hasAnyAuthority('Teacher', 'Student')")
    public List<Answer> getAllAnswersOrderBuVote(@PathVariable String idQuestion) {
        return answerService.getAnswersOrderByNbVote(idQuestion);
    }
}
