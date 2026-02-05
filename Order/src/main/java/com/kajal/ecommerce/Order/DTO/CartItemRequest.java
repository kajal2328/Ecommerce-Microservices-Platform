package com.kajal.ecommerce.Order.DTO;

import lombok.Data;

@Data
public class CartItemRequest
{
    private String productId;
    private Integer quantity;
}
