package com.example.event.repositorys;

import com.example.event.entities.Rsvp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsvpRepo extends JpaRepository<Rsvp, Long> {

}
