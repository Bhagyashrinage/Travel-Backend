package com.kavya.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false)
    private String departureCity;

    @Column(nullable = false)
    private String destinationCity;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, CANCELLED

    private LocalDateTime createdAt = LocalDateTime.now();
}
