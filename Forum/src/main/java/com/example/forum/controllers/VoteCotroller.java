package com.example.forum.controllers;

import com.example.forum.entities.RateQuestion;
import com.example.forum.entities.Votes;
import com.example.forum.services.VoteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/forum/ForumVote")

public class VoteCotroller {
    VoteServiceImpl voteService ;
    @PostMapping("/Teacher/create/{userId}/{answerId}")
    @PreAuthorize("hasAuthority('Teacher')")
    public Votes createVote(@RequestBody Votes v, @PathVariable String userId, @PathVariable String answerId) {

        return   voteService.add(v ,userId,answerId);


    }
    @PutMapping("/Teacher/update")
    @PreAuthorize("hasAuthority('Teacher')")
    public Votes updateVote(@RequestBody  Votes v) {
        return  voteService.update(v);

    }

    @DeleteMapping("/Teacher/{id}")
    @PreAuthorize("hasAuthority('Teacher')")
    public Void deleteVote(@PathVariable String id) {
        voteService.delete(id);

        return null;
    }

    @GetMapping("/Teacher/getvoteByUseAndAnswer/{idUser}/{idAnswer}")
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<?> getVoteByUserAndAnswer(@PathVariable String idUser, @PathVariable String idAnswer) {
        try {
            Votes vote = voteService.getVoteByUserAndAnswer(idUser, idAnswer);
            return ResponseEntity.ok(vote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            // Log the complete error details for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur: " + e.getMessage());
        }
    }
   /* @GetMapping("/Teacher/getvoteByUseAndAnswer/{idUser}/{idAnswer}")
    @PreAuthorize("hasAuthority('Teacher')")
    public Votes getVoteByUserAndAnswer(@PathVariable String idUser,@PathVariable String idAnswer) {
        return voteService.getVoteByUserAndAnswer(idUser,idAnswer);

    }*/
}
