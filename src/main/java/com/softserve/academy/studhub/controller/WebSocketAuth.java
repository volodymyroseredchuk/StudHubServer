package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.service.SocketTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.EncodeException;

@CrossOrigin
@RestController
@AllArgsConstructor
public class WebSocketAuth {

    private SocketTokenService tokenService;

    @GetMapping("/getSocketToken")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getToken(@RequestParam(value="param2") String id) {

        try {

            String token = tokenService.generateToken(Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK).body(token);

        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Generating token error.");
        }

    }

}
