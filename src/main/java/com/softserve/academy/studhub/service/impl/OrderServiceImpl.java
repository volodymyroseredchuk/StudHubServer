package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.*;
import com.softserve.academy.studhub.entity.enums.TaskStatus;
import com.softserve.academy.studhub.repository.OrderRepository;
import com.softserve.academy.studhub.repository.ResultSubmissionRepository;
import com.softserve.academy.studhub.repository.TaskRepository;
import com.softserve.academy.studhub.repository.UserRepository;
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
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public Order create(Task task, Proposal proposal) {
        Order order = new Order();

        task.setStatus(TaskStatus.IN_PROGRESS);
        task = taskRepository.saveAndFlush(task);
        order.setTask(task);

        User userCreator = task.getUser();
        userCreator.setCookiesCount(userCreator.getCookiesCount() - proposal.getPrice());
        userRepository.saveAndFlush(userCreator);
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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ORDER_NOT_EXIST + orderId));
    }

    @Override
    @Transactional
    public ResultSubmission submitResult(Integer orderId, ResultSubmission resultSubmission) {
        resultSubmission = resultSubmissionRepository.saveAndFlush(resultSubmission);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ORDER_NOT_EXIST + orderId));
        order.setResultSubmission(resultSubmission);
        order = orderRepository.saveAndFlush(order);

        User userExecutor = order.getUserExecutor();
        userExecutor.setCookiesCount(userExecutor.getCookiesCount() + order.getProposal().getPrice());
        userRepository.saveAndFlush(userExecutor);

        Task task = order.getTask();
        task.setStatus(TaskStatus.DONE);
        task = taskRepository.saveAndFlush(task);
        order.setTask(task);

        return resultSubmission;
    }

    @Override
    @Transactional
    public Order cancelOrder(Integer orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ORDER_NOT_EXIST + orderId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME + username));
        order = returnMoney(order, user);
        order.getTask().setStatus(TaskStatus.FAILED);
        taskRepository.saveAndFlush(order.getTask());
        return order;
    }

    private Order returnMoney(Order order, User user) {
        if (isOrderExecutor(order, user) || isOrderExpired(order)){
            order.getUserCreator().setCookiesCount(order.getUserCreator().getCookiesCount()
                    + order.getProposal().getPrice());
        } else if (isOrderCreator(order, user)){
            order.getUserCreator().setCookiesCount(order.getUserCreator().getCookiesCount()
                    + (order.getProposal().getPrice() / 2));
            order.getUserExecutor().setCookiesCount(order.getUserExecutor().getCookiesCount()
                    + (order.getProposal().getPrice() / 2));
        } else {
            throw new IllegalArgumentException(ErrorMessage.USER_NEITHER_QUESTION_CREATOR_NOR_EXECUTOR);
        }
        userRepository.saveAndFlush(order.getUserCreator());
        userRepository.saveAndFlush(order.getUserExecutor());
        return order;
    }


    private boolean isOrderCreator(Order order, User user){
        return user.getUsername().equals(order.getUserCreator().getUsername());
    }

    private boolean isOrderExecutor(Order order, User user){
        return user.getUsername().equals(order.getUserExecutor().getUsername());
    }

    private boolean isOrderExpired(Order order){
        return order.getEndDate().isBefore(LocalDateTime.now());
    }


}
