package com.kavya.backend.repositories;

import com.kavya.backend.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCase(String departureCity, String destinationCity);

}
