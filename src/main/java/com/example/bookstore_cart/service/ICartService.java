package com.example.bookstore_cart.service;

import com.example.bookstore_cart.dto.CartDto;
import com.example.bookstore_cart.model.Cart;
import org.json.JSONException;

import java.util.List;

public interface ICartService {
    Cart addCart(CartDto cartDto) throws JSONException;

    List<Cart> findAll();

    Cart FindById(Long cartid);

    String deleteById(Long userId,Long cartid);

    Cart updateCartData(Long cartid, CartDto cartDto);

    Cart changeCartQty(Long cartid, int quantity);

    List<Cart> getCartDetailsByUserId(Long userid);

    List<Cart> getCartDetailsByToken(String token);
}

