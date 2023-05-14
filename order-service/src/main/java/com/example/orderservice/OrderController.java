package com.example.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final List<Order> orders = Arrays.asList(
            new Order(1, 1, "Product 1"),
            new Order(2, 1, "Product 2"),
            new Order(3, 2, "Product 3"),
            new Order(4, 1, "Product 4"),
            new Order(5, 2, "Product 5"));

    private final Environment environment;

    @Autowired
    public OrderController(final Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/")
    public ResponseWrapper<List<Order>> getAllOrders(@RequestParam(required = false) Integer customerId) {
        if (customerId != null) {
            return new ResponseWrapper<>(
                    environment,
                    orders.stream()
                            .filter(order -> customerId.equals(order.getCustomerId()))
                            .collect(Collectors.toList()));
        }

        return new ResponseWrapper<>(environment, orders);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}