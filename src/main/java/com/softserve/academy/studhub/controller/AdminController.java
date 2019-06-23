package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.dto.MessageResponse;
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
    public ResponseEntity<?> raiseUserToModerator(@PathVariable("userId") Integer userId) {

        adminService.addRole(userId, RoleName.ROLE_MODERATOR);
        return ResponseEntity.ok(new MessageResponse("User has been successfully raised to Moderator"));
    }

    @PostMapping("/downToUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> downModeratorToUser(@PathVariable("userId") Integer moderatorId) {

        adminService.removeRole(moderatorId, RoleName.ROLE_MODERATOR);
        return ResponseEntity.ok(new MessageResponse("Moderator has been successfully downed to user"));
    }

}