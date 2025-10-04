package com.kavya.backend.services;

import com.kavya.backend.entities.Order;
import com.kavya.backend.entities.User;
import java.util.List;

public interface OrderServciceNew {

    public Order placeOrder(User user);
    public List<Order> getOrders(User user);
}
