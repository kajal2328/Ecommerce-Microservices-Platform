package com.kajal.ecommerce.Order.Service;

import com.kajal.ecommerce.Order.DTO.OrderCreatedEvent;
import com.kajal.ecommerce.Order.DTO.OrderItemDTO;
import com.kajal.ecommerce.Order.DTO.OrderResponse;
import com.kajal.ecommerce.Order.ENUM.OrderStatus;
import com.kajal.ecommerce.Order.Repository.OrderRepository;
//import com.app.ecom_application.Repository.UserRepository;
import com.kajal.ecommerce.Order.entity.Cart;
import com.kajal.ecommerce.Order.entity.OrderItem;
import com.kajal.ecommerce.Order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
//    private UserRepository userRepository;
    private final CartService cartService;

    private final StreamBridge streamBridge;

//    private final RabbitTemplate rabbitTemplate;
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;


    public Optional<OrderResponse> createOrder(String userId)
    {
        //validate for cart items
        List<Cart> cartItems= cartService.getAllItems(userId);

        if(cartItems.isEmpty())
        {
            return Optional.empty();
        }
        //validate for user
//        Optional<User> userOpt= userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty())
//        {
//            return Optional.empty();
//        }
//
//        User user= userOpt.get();

        //calculate total price
        BigDecimal totalPrice= cartItems.stream()
                .map(Cart::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //create order

        Orders order = new Orders();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems= cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();

        order.setItems(orderItems);

        Orders savedOrder=orderRepository.save(order);

        //clear the cart
        cartService.clearCart(userId);

        // Publish order created event

        OrderCreatedEvent event=new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getStatus(),
                mapToOrderItemDTO(savedOrder.getItems()),
                savedOrder.getTotalAmount(),
                savedOrder.getCreatedAt()
        );

//        rabbitTemplate.convertAndSend("order.exchange",
//                "order.tracking",
//                Map.of("orderId",savedOrder.getId(), "status", "created"));

//        rabbitTemplate.convertAndSend(exchangeName,
//                routingKey,
//                Map.of("orderId",savedOrder.getId(), "status", "created"));

//                rabbitTemplate.convertAndSend(exchangeName,
//                routingKey,
//                event);

        streamBridge.send("createOrder-out-0", event);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private List<OrderItemDTO> mapToOrderItemDTO(List<OrderItem> items)
    {
        return items.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                )).collect(Collectors.toUnmodifiableList());
    }

    private OrderResponse mapToOrderResponse(Orders order)
    {
        return new OrderResponse(
                        order.getId(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getItems().stream()
                                .map(orderItem -> new OrderItemDTO(
                                        orderItem.getId(),
                                        orderItem.getProductId(),
                                        orderItem.getQuantity(),
                                        orderItem.getPrice(),
                                        orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()
                                        ))
                                ))
                                .toList(),

                        order.getCreatedAt()

                );

    }
}
