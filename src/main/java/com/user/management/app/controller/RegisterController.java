package com.user.management.app.controller;

import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.RegisterUserDto;
import com.user.management.app.service.api.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.user.management.app.constant.Resource.*;

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
        registerService.verificationUser(token);
        return ResponseEntity.ok("");
    }

    @PostMapping(BULK_REGISTER)
    public ResponseEntity bulkRegister( @Valid @RequestHeader("Authorization") String authorization,
                                        @RequestParam("userFile") MultipartFile userFile) throws IOException {
        registerService.bulkRegister(authorization, userFile);
        return ResponseEntity.ok("");
    }

    @GetMapping(VALIDATE_USER)
    public ResponseEntity<Integer> validateUser(@PathVariable("username") String username ){
        return ResponseEntity.ok(registerService.validateUser(username));
    }

    @PostMapping(COMPLETE_REGISTER)
    public ResponseEntity registerUser(@RequestBody CredentialDto credentialDto){
        registerService.completeRegister(credentialDto);
        return ResponseEntity.ok("");
    }
}
