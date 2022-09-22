package com.example.bookstore_cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private String bookName;
    private int price;
    private String authorName;
    private int bookQuantity;
    private String Bookimage;
    private String bookDescription;
}
