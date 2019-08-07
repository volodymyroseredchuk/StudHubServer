package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.*;

import java.util.List;

public interface OrderService {

    Order create(Task task, Proposal proposal);

    List<Order> findByUserCreatorUsername(String userCreatorUsername);

    List<Order> findByUserExecutorUsername(String userExecutorUsername);

    Order findById(Integer orderId);

    ResultSubmission submitResult(Integer orderId, ResultSubmission resultSubmission);

    Order cancelOrder(Integer orderId, String username);
}
