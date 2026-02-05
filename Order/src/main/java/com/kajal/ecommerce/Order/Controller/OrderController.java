package com.kajal.ecommerce.Order.Controller;

import com.kajal.ecommerce.Order.DTO.OrderResponse;
import com.kajal.ecommerce.Order.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController
{
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-User-ID") String userId)
    {
        return new ResponseEntity<OrderResponse>(orderService.createOrder(userId).orElseThrow(()-> new RuntimeException("Order creation failed"))
                , HttpStatus.CREATED);
    }
}
