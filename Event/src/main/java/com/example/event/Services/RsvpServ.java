package com.example.event.Services;

import com.example.event.entities.Rsvp;
import com.example.event.repositorys.RsvpRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RsvpServ implements IService<Rsvp>{

    private final RsvpRepo repo;
    @Override
    public Rsvp Create(Rsvp rsvp) {
        return repo.save(rsvp);
    }

    @Override
    public Rsvp Update(Rsvp rsvp) {
        return repo.save(rsvp);
    }

    @Override
    public Rsvp Retrieve(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Rsvp> Retrieve() {
        return repo.findAll();
    }

    @Override
    public void Delete(Long id) {
        repo.deleteById(id);
    }
}
