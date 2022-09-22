package com.example.bookstore_cart.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {
    Long userid;
    Long bookId;
    int quantity;

}
