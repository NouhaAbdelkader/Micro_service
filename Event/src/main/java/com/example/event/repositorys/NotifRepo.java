package com.example.event.repositorys;

import com.example.event.entities.Notif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifRepo extends JpaRepository<Notif, Long> {
}
