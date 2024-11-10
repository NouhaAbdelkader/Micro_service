package com.example.event.Services;

import com.example.event.entities.Notif;
import com.example.event.repositorys.NotifRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotifServ implements IService<Notif>{

    private final NotifRepo repo;
    @Override
    public Notif Create(Notif notif) {
        return repo.save(notif);
    }

    @Override
    public Notif Update(Notif notif) {
        return repo.save(notif);
    }

    @Override
    public Notif Retrieve(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Notif> Retrieve() {
        return repo.findAll();
    }

    @Override
    public void Delete(Long id) {
        repo.deleteById(id);
    }
}
