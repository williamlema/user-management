package com.user.management.app.controller;

import com.user.management.app.model.entity.User;
import com.user.management.app.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.user.management.app.constant.Resource.ALL_USER;
import static com.user.management.app.constant.Resource.USER;

/**
 * CRUD controller for user entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(USER)
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(ALL_USER)
    public ResponseEntity<List<User>> getAll(
            @Valid
            @RequestHeader("Authorization") String authorization
    ){
        return ResponseEntity.ok(userService.getAll(authorization));
    }

    @GetMapping
    public ResponseEntity<User> get(
            @Valid
            @RequestHeader("Authorization") String authorization
    ){
        return ResponseEntity.ok(userService.get(authorization));
    }

    @CrossOrigin
    @PatchMapping
    public ResponseEntity<User> update(
            @Valid
            @RequestHeader("Authorization") String authorization,
            @Valid
            @RequestBody User user
    ){
        return ResponseEntity.ok(userService.update(authorization, user));
    }
}
