package com.kavya.backend.services;

import com.kavya.backend.dto.FlightRequest;
import com.kavya.backend.dto.FlightResponse;

public interface FlightService {

    FlightResponse addFlight(FlightRequest req);
    public long getTotalFlights();

}
