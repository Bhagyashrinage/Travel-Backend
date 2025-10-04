package com.kavya.backend.controllers;

import com.kavya.backend.entities.Order;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.UserRepository;
import com.kavya.backend.services.JwtService;
import com.kavya.backend.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final JwtService jwtService;
    private final UserRepository userRepo;

    @PostMapping("/checkout")
    public ResponseEntity<Order> placeOrder(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepo.findByEmail(email);
        Order order = orderService.placeOrder(user);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepo.findByEmail(email);
        return ResponseEntity.ok(orderService.getOrders(user));
    }

    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestParam double amount) {
        // simulate random payment success
        boolean success = Math.random() > 0.2;
        return ResponseEntity.ok(success ? "Payment Successful" : "Payment Failed");
    }
}

