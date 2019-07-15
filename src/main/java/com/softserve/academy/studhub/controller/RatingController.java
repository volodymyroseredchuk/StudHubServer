package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.CustomerDTO;
import com.softserve.academy.studhub.dto.FreelancerDTO;
import com.softserve.academy.studhub.dto.TaskDTO;
import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.service.CustomerService;
import com.softserve.academy.studhub.service.FreelancerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/rating")
public class RatingController {

    private final CustomerService customerService;
    private final FreelancerService freelancerService;
    private final ModelMapper modelMapper;

    @PostMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerDTO> rateCustomer(@Valid @RequestBody CustomerDTO customerDTO, Principal principal) {

        Customer customer = customerService.add(modelMapper.map(customerDTO, Customer.class), principal);

        return ResponseEntity.ok().body(modelMapper.map(customer, CustomerDTO.class));
    }

    @PostMapping("/freelancer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FreelancerDTO> rateFreelancer(@Valid @RequestBody FreelancerDTO freelancerDTO, Principal principal) {

        Freelancer freelancer = freelancerService.add(modelMapper.map(freelancerDTO, Freelancer.class), principal);

        return ResponseEntity.ok().body(modelMapper.map(freelancer, FreelancerDTO.class));
    }
}
