package com.user.management.app.controller;

import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.TokenDto;
import com.user.management.app.service.api.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.user.management.app.constant.Resource.*;

@RestController
@RequestMapping(AUTHENTICATION)
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(LOGIN)
    public ResponseEntity<TokenDto> login(@RequestBody CredentialDto credentials){
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

    @PostMapping(LOGOUT)
    public ResponseEntity logout(@Valid @RequestHeader("Authorization") String authorization){
        authenticationService.logout(authorization);
        return ResponseEntity.ok("");
    }
}
