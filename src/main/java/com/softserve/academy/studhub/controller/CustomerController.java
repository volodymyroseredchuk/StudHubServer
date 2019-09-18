package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.CustomerForRatingDTO;
import com.softserve.academy.studhub.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/rating/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CustomerForRatingDTO> getSumOfRatingByUserUsername(@PathVariable String username) {

        return ResponseEntity.ok(customerService.getRatingByUserUsername(username));
    }
}
