package com.kajal.ecommerce.notification.Service;

//import com.ecommerce.notification.payload.OrderCreatedEvent;
//import com.ecommerce.notification.payload.OrderStatus;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.kajal.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
public class OrderEventConsumer
{
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent orderEvent)
//    {
//        System.out.println("Received Order Event: " + orderEvent);
//
//        long orderId= orderEvent.getOrderId();
//        OrderStatus orderStatus= orderEvent.getOrderStatus();
//
//        System.out.println("Order ID: " + orderId);
//        System.out.println("Order Status: " + orderStatus);
//    }


    @Bean
    public Consumer<OrderCreatedEvent> orderCreated()
    {
        return event -> {
            log.info("Received Order Created Event for Order: {}", event.getOrderId());
            log.info("Received Order Created Event for user id: {}", event.getUserId());
        };
    }
}
