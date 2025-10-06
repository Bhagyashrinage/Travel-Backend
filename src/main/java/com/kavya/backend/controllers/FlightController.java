package com.kavya.backend.controllers;

import com.kavya.backend.dto.FlightRequest;
import com.kavya.backend.dto.FlightResponse;
import com.kavya.backend.entities.Flight;
import com.kavya.backend.repositories.FlightRepository;
import com.kavya.backend.services.FlightService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<FlightResponse> addFlight(@Valid @RequestBody FlightRequest request) {
        FlightResponse response = flightService.addFlight(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightResponse> response = flights.stream()
                .map(flight -> modelMapper.map(flight, FlightResponse.class))
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
            @RequestParam String from,
            @RequestParam String to) {

        List<Flight> flights = flightRepository.findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCase(from, to);

        List<FlightResponse> response = flights.stream()
                .map(flight -> modelMapper.map(flight, FlightResponse.class))
                .toList();

        return ResponseEntity.ok(response);
    }

}
