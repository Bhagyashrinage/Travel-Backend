package com.kavya.backend.services;

import com.kavya.backend.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    public String generateToken(User user);
    public String extractUsername(String token);
    public boolean validateToken(String token, UserDetails user);
}
