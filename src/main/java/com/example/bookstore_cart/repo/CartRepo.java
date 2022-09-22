package com.example.bookstore_cart.repo;

import com.example.bookstore_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo  extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE userid=:id", nativeQuery = true)
    List<Cart> getCartListWithUserId(Long id);
}