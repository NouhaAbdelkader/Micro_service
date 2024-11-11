package com.example.event.controllers;


import com.example.event.Services.NotifServ;
import com.example.event.entities.Notif;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/event/notif")
public class NotifController {

    private final NotifServ serv;

    @GetMapping("/show")
    public List<Notif> getAll(){
        return serv.Retrieve();
    }

}
