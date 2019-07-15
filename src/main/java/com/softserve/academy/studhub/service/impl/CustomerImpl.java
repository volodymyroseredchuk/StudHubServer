package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.repository.CustomerRepository;
import com.softserve.academy.studhub.service.CustomerService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class CustomerImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;

    @Override
    public Customer add(Customer customer, Principal principal) {

        customer.setUser(userService.findByUsername(principal.getName()));
        return customerRepository.saveAndFlush(customer);
    }
}