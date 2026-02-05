package com.kajal.ecommerce.Order.clients;

import com.kajal.ecommerce.Order.DTO.ProductResponse;
import com.kajal.ecommerce.Order.DTO.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient
{

    @GetExchange("/users/get/{id}")
    UserResponse getUserDetails(@PathVariable String id);
}
