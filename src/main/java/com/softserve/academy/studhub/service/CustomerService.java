package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.CustomerForRatingDTO;
import com.softserve.academy.studhub.entity.Customer;

public interface CustomerService {

    Customer add(Customer customer, Integer orderId);

    CustomerForRatingDTO getRatingByUserUsername(String username);
}
