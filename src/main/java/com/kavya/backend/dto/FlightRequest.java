package com.kavya.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Departure city is required")
    private String departureCity;

    @NotBlank(message = "Destination city is required")
    private String destinationCity;

    @Positive(message = "Base price must be positive")
    private double basePrice;

    @Positive(message = "Total seats must be greater than zero")
    private int totalSeats;

}
