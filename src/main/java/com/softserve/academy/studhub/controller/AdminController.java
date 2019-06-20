package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.security.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/raiseToModerator/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> raiseUserToModerator(@PathVariable("userId") Integer userId) {

        try {

            adminService.raiseToModerator(userId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<String>(exception.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body("User raised to moderator successfully!");
    }

    @PostMapping("/downToUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> downModeratorToUser(@PathVariable("userId") Integer moderatorId) {

        try {

            adminService.downToUser(moderatorId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<String>(exception.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body("Moderator downed to user successfully!");
    }

}