package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.repository.CustomerRepository;
import com.softserve.academy.studhub.service.CustomerService;
import com.softserve.academy.studhub.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    @Override
    public Customer add(Customer customer, Integer orderId) {

        Order order = orderService.findById(orderId);
        customer.setUser(order.getUserCreator());
        order.setCustomer(customer);

        return customerRepository.saveAndFlush(customer);
    }
}