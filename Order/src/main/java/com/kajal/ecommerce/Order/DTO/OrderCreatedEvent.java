package com.kajal.ecommerce.Order.DTO;

import com.kajal.ecommerce.Order.ENUM.OrderStatus;
import com.kajal.ecommerce.Order.entity.OrderItem;
import jakarta.persistence.NamedQueries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent
{
    private Long orderId;
    private String userId;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

}
