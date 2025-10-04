package com.kavya.backend.repositories;

import com.kavya.backend.entities.Order;
import com.kavya.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
