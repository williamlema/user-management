package com.user.management.app.controller;

import com.user.management.app.model.dto.RegisterUserDto;
import com.user.management.app.service.api.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.user.management.app.constant.Resource.REGISTER;
import static com.user.management.app.constant.Resource.VALIDATE;

@RestController
@RequestMapping(REGISTER)
public class RegisterController {

    public final IRegisterService registerService;

    @Autowired
    public RegisterController(IRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody RegisterUserDto newUser){
        registerService.registerNewUser(newUser);
        return ResponseEntity.ok("");
    }

    @GetMapping(VALIDATE)
    public ResponseEntity registerUser(@PathVariable("token") String token ){
        registerService.validateUser(token);
        return ResponseEntity.ok("");
    }
}
