package com.kavya.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private double basePrice;
    private int totalSeats;
    private String status;
    private LocalDateTime createdAt;
}
