package com.example.webpos.biz;

import com.example.webpos.model.Cart;

public interface OrderService {
    public void generateOrder(Cart cart);
}
