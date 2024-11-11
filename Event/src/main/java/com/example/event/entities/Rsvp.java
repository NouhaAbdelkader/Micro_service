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
public class Rsvp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming the ID is auto-generated in PostgreSQL
    private Long id;


    private String userID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String status;

    @Override
    public String toString() {
        return "Rsvp{" +
                "id='" + id + '\'' +
                ", userID=" + userID +
                ", event=" + event +
                ", status='" + status + '\'' +
                '}';
    }
}
