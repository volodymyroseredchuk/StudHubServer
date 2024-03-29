package com.softserve.academy.studhub.security.controller;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.security.services.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/raiseToModerator/{userId}")
    @PreAuthorize("hasAuthority('GRAND_ROLE_PRIVILEGE')")
    public ResponseEntity<MessageResponse> raiseUserToModerator(@PathVariable("userId") Integer userId) {

        adminService.addRole(userId, RoleName.ROLE_MODERATOR);
        return ResponseEntity.ok(new MessageResponse(SuccessMessage.USER_RAISED));
    }

    @PostMapping("/downToUser/{userId}")
    @PreAuthorize("hasAuthority('GRAND_ROLE_PRIVILEGE')")
    public ResponseEntity<MessageResponse> downModeratorToUser(@PathVariable("userId") Integer moderatorId) {

        adminService.removeRole(moderatorId, RoleName.ROLE_MODERATOR);
        return ResponseEntity.ok(new MessageResponse(SuccessMessage.MODERATOR_DOWNED));
    }

}