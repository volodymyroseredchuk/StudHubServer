package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Customer;

import java.security.Principal;

public interface CustomerService {

    Customer add(Customer customer, Principal principal);
}
