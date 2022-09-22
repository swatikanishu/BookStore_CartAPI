package com.example.bookstore_cart.service;


import com.example.bookstore_cart.dto.BookDto;
import com.example.bookstore_cart.dto.CartDto;
import com.example.bookstore_cart.dto.UserDto;
import com.example.bookstore_cart.exception.CartException;
import com.example.bookstore_cart.model.Cart;
import com.example.bookstore_cart.repo.CartRepo;
import com.example.bookstore_cart.utility.EmailSenderService;
import com.example.bookstore_cart.utility.TokenUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

@Service
public class CartService implements  ICartService {
    @Autowired
    CartRepo cartRepo;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public Cart addCart(CartDto cartDto)  {
        UserDto userData = restTemplate.getForObject("http://localhost:8081/User/Get/" + cartDto.getUserid(), UserDto.class);
//        ResponseEntity<UserDto> userDetails = restTemplate.getForEntity("http://localhost:8081/User/Get/"+cartDto.getUserid(), UserDto.class);
//

        //Converting to the JSON object
//        JSONObject user = new JSONObject(userData);
        //printing User details in JSON Object form
//        System.out.println(user);
//        System.out.println(user.get("email_address"));

        BookDto bookDetails = restTemplate.getForObject("http://localhost:8082/Book/get/" + cartDto.getBookId(), BookDto.class);
        if (userData!=null && bookDetails!=null) {
            Cart cartDetails = new Cart(cartDto);
            return cartRepo.save(cartDetails);
        } else {
            throw new CartException("invalid user Id and BookID");

        }

    }

    @Override
    public List<Cart> findAll() {
        List<Cart> cartdetails  = cartRepo.findAll();
        return cartdetails;
    }
    // finding the car details using cartid
    @Override
    public Cart FindById(Long cartid) {
        Cart cart = cartRepo.findById(cartid).orElse(null);
        if (cart != null) {
            return cart;

        }else
            throw new CartException("card id is not found");
    }

    @Override
    public String deleteById(Long userId,Long cartid) {
        Cart findById = cartRepo.findById(cartid).orElse(null);
        UserDto userData = restTemplate.getForObject("http://localhost:8081/User/Get/" + userId, UserDto.class);
        if (findById != null&& userData!=null) {
           if(userData.getUserid().equals(findById.getUserid())) {
               cartRepo.deleteById(cartid);
               return "data is deleted";
           }else
               throw new CartException("userid does not match");
        } else throw new CartException("id is invalid");
    }

    @Override
    public Cart updateCartData(Long id, CartDto cartDto) {
        UserDto userData = restTemplate.getForObject("http://localhost:8081/User/Get/" + cartDto.getUserid(), UserDto.class);

        BookDto bookDetails = restTemplate.getForObject("http://localhost:8082/Book/get/" + cartDto.getBookId(), BookDto.class);

        Cart editdata = cartRepo.findById(id).orElse(null);
        if (userData!=null&&bookDetails!=null&&editdata!=null) {
            editdata.setBookid(cartDto.getBookId());
            editdata.setUserid(cartDto.getUserid());
            editdata.setQuantity(cartDto.getQuantity());
            return cartRepo.save(editdata);

        }else
            throw new CartException(" id is invalid");
    }
    @Override
    public Cart changeCartQty(Long cartid, int quantity) {
        Cart cart = cartRepo.findById(cartid).orElse(null);
        if(cart == null){
            throw new CartException("id is not found");
        }
        cart.setQuantity(quantity);
        return cartRepo.save(cart);
    }

    @Override
    public List<Cart> getCartDetailsByUserId(Long userid) {
        List<Cart> userCartList = cartRepo.getCartListWithUserId(userid);
        if(userCartList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return userCartList;
    }
    @Override
    public List<Cart> getCartDetailsByToken(String token) {
        Long userId = tokenUtil.decodeToken(token);
        List<Cart> userCartList = cartRepo.getCartListWithUserId(userId);
        if(userCartList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return userCartList;
    }

}









