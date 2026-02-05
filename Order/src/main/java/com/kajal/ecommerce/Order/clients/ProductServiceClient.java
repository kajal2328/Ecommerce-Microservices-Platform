package com.kajal.ecommerce.Order.clients;

import com.kajal.ecommerce.Order.DTO.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient
{

    @GetExchange("/products/get/{id}")
    ProductResponse getProductDetails(@PathVariable String id);
}
