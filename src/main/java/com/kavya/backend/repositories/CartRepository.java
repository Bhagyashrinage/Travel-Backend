package com.kavya.backend.repositories;

import com.kavya.backend.entities.CartItem;
import com.kavya.backend.entities.Product;
import com.kavya.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
