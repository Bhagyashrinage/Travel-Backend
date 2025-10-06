package com.kavya.backend.services.impl;

import com.kavya.backend.dto.FlightRequest;
import com.kavya.backend.dto.FlightResponse;
import com.kavya.backend.entities.Flight;
import com.kavya.backend.exception.ResourceAlreadyExistsException;
import com.kavya.backend.repositories.FlightRepository;
import com.kavya.backend.services.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl  implements FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;


    @Override
    public FlightResponse addFlight(FlightRequest req) {

        // Check for duplicate flight numbers
        if (flightRepository.existsByFlightNumber(req.getFlightNumber())) {
            throw new ResourceAlreadyExistsException("Flight with number " + req.getFlightNumber() + " already exists!");
        }

        // Convert DTO -> Entity
        Flight flight = modelMapper.map(req, Flight.class);
        flight.setStatus("ACTIVE");

        // Save in DB
        Flight savedFlight = flightRepository.save(flight);

        // Convert Entity -> DTO for response
        return modelMapper.map(savedFlight, FlightResponse.class);
    }

    @Override
    public long getTotalFlights() {
        return flightRepository.count();
    }
}
