package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.ResultSubmission;
import com.softserve.academy.studhub.entity.Task;

import java.util.List;

public interface OrderService {

    Order create(Task task, Proposal proposal);
    List<Order> findByUserCreatorUsername(String userCreatorUsername);
    List<Order> findByUserExecutorUsername(String userExecutorUsername);
    Order findById(Integer orderId);
    ResultSubmission submitResult(Integer orderId, ResultSubmission resultSubmission);

    Integer countByFreelancerAndTaskDone(String username);
}
