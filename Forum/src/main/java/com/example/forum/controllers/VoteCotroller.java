package com.example.forum.controllers;

import com.example.forum.entities.Votes;
import com.example.forum.services.VoteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/ForumVote")

public class VoteCotroller {
    VoteServiceImpl voteService ;
    @PostMapping("/create/{userId}/{answerId}")
    public Votes createVote(@RequestBody Votes v, @PathVariable String userId, @PathVariable String answerId) {

        return   voteService.add(v ,userId,answerId);


    }
    @PutMapping("/update")
    public Votes updateVote(@RequestBody  Votes v) {
        return  voteService.update(v);

    }

    @DeleteMapping("/{id}")
    public Void deleteVote(@PathVariable String id) {
        voteService.delete(id);

        return null;
    }

    @GetMapping("/getvoteByUseAndAnswer/{idUser}/{idAnswer}")
    public Votes getVoteByUserAndAnswer(@PathVariable String idUser,@PathVariable String idAnswer) {
        return voteService.getVoteByUserAndAnswer(idUser,idAnswer);

    }
}
