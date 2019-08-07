package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.FreelancerForRatingDTO;
import com.softserve.academy.studhub.service.FreelancerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/freelancer")
public class FreelancerController {

    private final FreelancerService freelancerService;

    @GetMapping("/rating/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<FreelancerForRatingDTO> getSumOfRatingByUserUsername(@PathVariable String username) {

        return ResponseEntity.ok(freelancerService.getRatingByUserUsername(username));
    }
}