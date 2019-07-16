package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
