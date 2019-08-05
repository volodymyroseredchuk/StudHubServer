package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.dto.CustomerDTO;
import com.softserve.academy.studhub.dto.FreelancerDTO;
import com.softserve.academy.studhub.dto.OrderDTO;
import com.softserve.academy.studhub.dto.ResultSubmissionDTO;
import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.entity.ResultSubmission;
import com.softserve.academy.studhub.service.CustomerService;
import com.softserve.academy.studhub.service.FreelancerService;
import com.softserve.academy.studhub.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private final CustomerService customerService;
    private final FreelancerService freelancerService;
    private final ModelMapper modelMapper;

    @GetMapping("/{orderId}")
    @PreAuthorize("@orderServiceImpl.findById(#orderId).getUserExecutor().getUsername() == principal.username or " +
            "@orderServiceImpl.findById(#orderId).getUserCreator().getUsername() == principal.username")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(modelMapper.map(orderService.findById(orderId), OrderDTO.class));
    }

    @GetMapping("/created/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDTO>> getOrderAsTaskCreator(Principal principal) {
        return ResponseEntity.ok(orderService.findByUserCreatorUsername(principal.getName())
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/assigned/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDTO>> getOrderAsTaskExecutor(Principal principal) {
        return ResponseEntity.ok(orderService.findByUserExecutorUsername(principal.getName())
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/{orderId}/submit")
    @PreAuthorize("@orderServiceImpl.findById(#orderId).getUserExecutor().getUsername() == principal.username")
    public ResponseEntity<ResultSubmission> submitResult(@RequestBody ResultSubmissionDTO resultSubmissionDTO,
                                                         @PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.submitResult(
                orderId,
                modelMapper.map(resultSubmissionDTO, ResultSubmission.class)
        ));
    }

    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("@orderServiceImpl.findById(#orderId).getUserExecutor().getUsername() == principal.username or " +
            "@orderServiceImpl.findById(#orderId).getUserCreator().getUsername() == principal.username")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Integer orderId,
                                                Principal principal) {
        return ResponseEntity.ok(modelMapper.map(orderService.cancelOrder(orderId, principal.getName()), OrderDTO.class));
    }

    @PostMapping("/{orderId}/feedback/customer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerDTO> rateCustomer(@PathVariable Integer orderId, @RequestBody CustomerDTO customerDTO) {

        Customer customer = customerService.add(modelMapper.map(customerDTO, Customer.class), orderId);
        return ResponseEntity.ok(modelMapper.map(customer, CustomerDTO.class));
    }

    @PostMapping("/{orderId}/feedback/freelancer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> rateFreelancer(@PathVariable Integer orderId, @RequestBody FreelancerDTO freelancerDTO) {

        Freelancer freelancer = freelancerService.add(modelMapper.map(freelancerDTO, Freelancer.class), orderId);
        return ResponseEntity.ok(modelMapper.map(freelancer, FreelancerDTO.class));
    }
}
