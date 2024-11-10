package com.example.event.controllers;

import com.example.event.Services.EventServ;
import com.example.event.entities.Event;
import com.example.event.entities.Notif;
import com.example.event.repositorys.NotifRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/event")
public class EventController {

    private final EventServ serv;
    private final NotifRepo repo;
    @PostMapping("/add")
    public Event add(@RequestBody Event event ){

        Notif notif = new Notif();
        notif.setEvent(event);
        serv.Create(event);
        repo.save(notif);
        return event;
    }
    @PutMapping("/update")
    public Event Update(@RequestBody Event Event){
        return serv.Update(Event);
    }

    @GetMapping("/show/{id}")
    public Event Get(@PathVariable Long id){
        return serv.Retrieve(id);
    }

    @GetMapping("/show")
    public List<Event> getAll(){
        return serv.Retrieve();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        serv.Delete(id);
    }


    @PostMapping("/stats")
    public Event getStats(@RequestBody Event event){
        return serv.GetEstimation(event);
    }

    @PostMapping("/estimations")
    public void getAllStats(){
        serv.GetAllEstimation();
    }
}
