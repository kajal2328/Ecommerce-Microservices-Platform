package com.kajal.ecommerce.Order.Controller;

import com.kajal.ecommerce.Order.DTO.CartItemRequest;
import com.kajal.ecommerce.Order.Service.CartService;
import com.kajal.ecommerce.Order.entity.Cart;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController
{
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                            @RequestBody CartItemRequest request) {
        String resultMessage = cartService.addCart(userId, request);

        // Map of known error messages and corresponding HTTP Status
        Map<String, HttpStatus> errorMapping = Map.of(
                "Product not found", HttpStatus.BAD_REQUEST,
                "User not found", HttpStatus.BAD_REQUEST,
                "Product Out of Stock", HttpStatus.BAD_REQUEST
        );

        // Check if resultMessage matches any of the error messages
        HttpStatus status = errorMapping.getOrDefault(resultMessage, HttpStatus.CREATED);

        return ResponseEntity.status(status).body(resultMessage);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Cart>> getAllCartItems(@RequestHeader("X-User-ID") String userId)
    {
        return ResponseEntity.ok(cartService.getAllItems(userId));
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteItem (@RequestHeader("X-User-ID") String userId,
                                            @PathVariable String productId )
    {
        boolean deleted= cartService.deleteItemFromCart(userId, productId);

        return deleted ? ResponseEntity.ok("Item deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
    }
}
