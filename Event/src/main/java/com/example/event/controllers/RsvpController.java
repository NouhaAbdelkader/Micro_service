package com.example.event.controllers;

import com.example.event.entities.Rsvp;
import com.example.event.repositorys.RsvpRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/event/rsvp")
public class RsvpController {

    private final RsvpRepo repo;

    @PostMapping("/add")
    public Rsvp Add(@RequestBody Rsvp rsvp){
        return repo.save(rsvp);
    }
}
