package com.example.bookstore_cart.model;
import com.example.bookstore_cart.dto.CartDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cartId", nullable = false)
    Long cartId;
    Long  userid;
    Long bookid;
    int quantity;
    // parameterized constructor
    public Cart(CartDto cartDto) {
        this.userid=cartDto.getUserid();
        this.bookid=cartDto.getBookId();
        this.quantity=cartDto.getQuantity();
    }


}

