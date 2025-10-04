package com.kavya.backend.services.impl;

import com.kavya.backend.entities.CartItem;
import com.kavya.backend.entities.Product;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.CartRepository;
import com.kavya.backend.repositories.ProductRepository;
import com.kavya.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    @Override
    public List<CartItem> getCart(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public void addCart(User user, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartItem> existing = cartRepo.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + 1);
            cartRepo.save(item);
        } else {
            cartRepo.save(new CartItem(null, user, product, 1));
        }
    }

    @Override
    public void removeCart(User user, Long productId) {
        cartRepo.findByUserAndProduct(user, productRepo.findById(productId).get())
                .ifPresent(cartRepo::delete);
    }
}
