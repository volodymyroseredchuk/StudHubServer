package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
