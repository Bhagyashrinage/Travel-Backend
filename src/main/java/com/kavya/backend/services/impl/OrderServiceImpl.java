package com.kavya.backend.services.impl;

import com.kavya.backend.entities.CartItem;
import com.kavya.backend.entities.Order;
import com.kavya.backend.entities.OrderItem;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.CartRepository;
import com.kavya.backend.repositories.OrderItemRepository;
import com.kavya.backend.repositories.OrderRepository;
import com.kavya.backend.repositories.ProductRepository;
import com.kavya.backend.services.OrderServciceNew;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServciceNew {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    @Override
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setPaymentStatus("SUCCESS");  // simulate success
        order.setOrderStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = cartItems.stream().map(ci -> {
            OrderItem item = new OrderItem();
            item.setProduct(ci.getProduct());
            item.setQuantity(ci.getQuantity());
            item.setPrice(ci.getProduct().getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepo.save(order);

        // Clear cart after order
        cartRepo.deleteAll(cartItems);
        return order;
    }

    @Override
    public List<Order> getOrders(User user) {
        return orderRepo.findByUser(user);
    }
}
