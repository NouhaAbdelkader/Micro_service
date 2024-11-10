package com.example.event.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notif {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageContent;

    @ManyToOne(fetch = FetchType.LAZY)  // Fetch type can be adjusted based on your requirements
    @JoinColumn(name = "event_id", nullable = false) // Foreign key column in Notif table
    private Event event;
}
