package com.example.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {
    private List<Customer> customers = Arrays.asList(
            new Customer(1, "John Doe"),
            new Customer(2, "Jane Doe"));

    private OrderClient orderClient;

    @Autowired
    public CustomerController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @GetMapping("/{id}/orders")
    public Object getOrdersForCustomer(@PathVariable int id) {
        return orderClient.getOrdersForCustomer(id);
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}