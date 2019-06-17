package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.service.SocketTokenService;
import com.softserve.academy.studhub.service.impl.SocketTokenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;


@RestController
public class WebSocketAuth {

    private SocketTokenService tokenService = new SocketTokenServiceImpl();

    @GetMapping("/getSocketToken")
    public ResponseEntity<Object> getToken() {

        try{

            String token = tokenService.generateToken();
            return ResponseEntity.status(HttpStatus.OK).body(token);

        } catch (IllegalArgumentException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EncodeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Generating token error.");
        }

    }

}
