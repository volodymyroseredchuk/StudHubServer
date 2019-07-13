package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.dto.OrderDTO;
import com.softserve.academy.studhub.dto.ResultSubmissionDTO;
import com.softserve.academy.studhub.entity.ResultSubmission;
import com.softserve.academy.studhub.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
public class OrderController {

    private OrderService orderService;

    private ModelMapper modelMapper;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Integer orderId){
        return ResponseEntity.ok(modelMapper.map(orderService.findById(orderId), OrderDTO.class));
    }

    @GetMapping("/orders/created/my}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDTO>> getOrderAsTaskCreator(Principal principal){
        return ResponseEntity.ok(orderService.findByUserCreatorUsername(principal.getName())
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/orders/assigned/my}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDTO>> getOrderAsTaskExecutor(Principal principal){
        return ResponseEntity.ok(orderService.findByUserExecutorUsername(principal.getName())
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/orders/{orderId}/submit")
    @PreAuthorize("@orderServiceImpl.findById(#orderId).getUserExecutor().getUsername() == principal.username")
    public ResponseEntity<ResultSubmission> submitResult(@RequestBody ResultSubmissionDTO resultSubmissionDTO,
                                                         @PathVariable Integer orderId){
        return ResponseEntity.ok(orderService.submitResult(
                orderId,
                modelMapper.map(resultSubmissionDTO,ResultSubmission.class)
        ));
    }
}
