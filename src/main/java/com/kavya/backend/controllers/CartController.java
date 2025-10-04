package com.kavya.backend.controllers;

import com.kavya.backend.entities.CartItem;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.UserRepository;
import com.kavya.backend.services.CartService;
import com.kavya.backend.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;
    private final UserRepository userRepo;

    @GetMapping
    public List<CartItem> getCart(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepo.findByEmail(email);
        return cartService.getCart(user);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String token,
                                       @PathVariable Long productId) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepo.findByEmail(email);
        cartService.addCart(user, productId);
        return ResponseEntity.ok("Added to cart");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@RequestHeader("Authorization") String token,
                                            @PathVariable Long productId) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepo.findByEmail(email);
        cartService.removeCart(user, productId);
        return ResponseEntity.ok("Removed from cart");
    }
}
