package com.user.management.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.user.management.app.constant.Resource.AUTHENTICATION;
import static com.user.management.app.constant.Resource.LOGIN;

@RestController
@RequestMapping(AUTHENTICATION)
public class AuthenticationController {

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("OK");
    }
}
