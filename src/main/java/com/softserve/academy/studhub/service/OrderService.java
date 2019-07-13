package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.ResultSubmission;
import com.softserve.academy.studhub.entity.Task;

import java.util.List;

public interface OrderService {

    Order create(Task task, Proposal proposal);
    List<Order> findByUserCreatorId(Integer userCreatorId);
    List<Order> findByUserExecutorId(Integer userExecutorId);
    Order findById(Integer orderId);
    ResultSubmission submitResult(Integer orderId, ResultSubmission resultSubmission)


}
