package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.repository.CustomerRepository;
import com.softserve.academy.studhub.service.CustomerService;
import com.softserve.academy.studhub.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    private CustomerService customerService;

    @Before
    public void init() {
        customerService = new CustomerServiceImpl(customerRepository, orderService);
    }

    @Test
    public void add() {

        Integer orderId = 1;
        Integer freelancerId = 1;

        Customer customer = new Customer();
        customer.setId(freelancerId);

        Order order = new Order();

        when(customerRepository.saveAndFlush(customer)).thenReturn(customer);
        when(orderService.findById(orderId)).thenReturn(order);

        Customer result = customerService.add(customer, orderId);

        Assert.assertEquals(result.getId(), customer.getId());
    }
}