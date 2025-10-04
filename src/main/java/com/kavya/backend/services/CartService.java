package com.kavya.backend.services;

import com.kavya.backend.entities.CartItem;
import com.kavya.backend.entities.User;
import java.util.List;

public interface CartService {

    public List<CartItem> getCart(User user);
    public void addCart(User user, Long productId);
    public void removeCart(User user, Long productId);
}
