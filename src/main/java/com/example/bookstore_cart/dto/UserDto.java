package com.example.bookstore_cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class UserDto {
    Long userid;
    String firstName;
    String lastName;
    String address;
    String email_address;
    LocalDate DOB;
    String password;
}
