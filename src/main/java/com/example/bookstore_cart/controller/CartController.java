package com.example.bookstore_cart.controller;
import com.example.bookstore_cart.dto.CartDto;
import com.example.bookstore_cart.dto.ResponseDto;
import com.example.bookstore_cart.model.Cart;
import com.example.bookstore_cart.service.ICartService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Autowired
    ICartService cartService;

    //add cart details
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addBook(@Valid @RequestBody CartDto cartDto) throws JSONException {
         Cart cart = cartService.addCart(cartDto);
        ResponseDto responseDTO = new ResponseDto("cart details added", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
    // get all cart data by findAll() method
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDto> findAllDetail() {
        List<Cart> cartList = cartService.findAll();
        ResponseDto responseDTO = new ResponseDto("** All cart List ** ", cartList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    // get cartdetails with cartid
    @GetMapping("/get/{cartId}")
    public ResponseEntity<ResponseDto> FindById(@PathVariable Long cartId) {
        Cart response = cartService.FindById(cartId);
        ResponseDto responseDto = new ResponseDto("***All Details cart list using Id***", response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    // Ability to delete cart details by id
    @DeleteMapping("/delete/{userId}/{cartId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable  Long userId,@PathVariable Long cartId) {
        cartService.deleteById(userId,cartId);
        ResponseDto reponseDTO = new ResponseDto("**cart Data deleted successfully ** ", "deleted id " + cartId);
        return new ResponseEntity(reponseDTO, HttpStatus.ACCEPTED);
    }
    // Ability to update  cart details by id
    @PutMapping("/update/{cartid}")
    public ResponseEntity<ResponseDto> editData(@PathVariable Long cartid, @Valid @RequestBody CartDto cartDto) {
        Cart cartData = cartService.updateCartData(cartid, cartDto);
        ResponseDto responseDTO = new ResponseDto("Updated Cart Details Successfully", cartData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Ability to update  quantity
    @PostMapping("/update-qty")
    public ResponseEntity<ResponseDto> changeBookQuantity(@RequestParam Long cartid, @RequestParam int quantity) {
        Cart cart = cartService.changeCartQty(cartid, quantity);
        ResponseDto responseDTO = new ResponseDto("Cart quantity changed successfully", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Cart Data by UserId
    @GetMapping("/UserCart/{userId}")
    public ResponseEntity<ResponseDto> getCartDataByUserID(@PathVariable Long Id){
        List<Cart> userCartDetails = cartService.getCartDetailsByUserId(Id);
        ResponseDto responseDTO = new ResponseDto("Cart Details with User ID: "+Id, userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    // Ability to get cart details by token
    @GetMapping("/UserCartToken/{token}")
    public ResponseEntity<ResponseDto> getCartDataByToken(@PathVariable String token){
        List<Cart> userCartDetails = cartService.getCartDetailsByToken(token);
        ResponseDto responseDTO = new ResponseDto("Cart Details with User ID: "+token, userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}

