package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.ResultSubmission;
import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.repository.OrderRepository;
import com.softserve.academy.studhub.repository.ResultSubmissionRepository;
import com.softserve.academy.studhub.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;
    private ResultSubmissionRepository resultSubmissionRepository;

    @Override
    @Transactional
    public Order create(Task task, Proposal proposal) {
        Order order = new Order();
        order.setTask(task);
        order.setProposal(proposal);
        order.setUserCreator(task.getUser());
        order.setUserExecutor(proposal.getUser());
        order.setStartDate(LocalDateTime.now());
        order.setEndDate(LocalDateTime.now().plusDays(proposal.getDaysCount()));
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public List<Order> findByUserCreatorUsername(String userCreatorUsername) {
        return orderRepository.findByUserCreatorUsername(userCreatorUsername);
    }

    @Override
    public List<Order> findByUserExecutorUsername(String userExecutorUsername) {
        return orderRepository.findByUserExecutorUsername(userExecutorUsername);
    }

    @Override
    public Order findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with this Id does not exist!"));
    }

    @Override
    @Transactional
    public ResultSubmission submitResult(Integer orderId, ResultSubmission resultSubmission) {
        resultSubmission = resultSubmissionRepository.saveAndFlush(resultSubmission);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with this Id does not exist!"));
        order.setResultSubmission(resultSubmission);
        orderRepository.saveAndFlush(order);
        return resultSubmission;
    }


}
