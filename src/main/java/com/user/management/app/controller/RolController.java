package com.user.management.app.controller;

import com.user.management.app.model.entity.Rol;
import com.user.management.app.service.api.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.user.management.app.constant.Resource.ROL;

/**
 * CRUD controller for user entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(ROL)
public class RolController {

    private final IRolService rolService;

    @Autowired
    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> get(
            @Valid
            @RequestHeader("Authorization") String authorization
    ){
        return ResponseEntity.ok(rolService.getAll(authorization));
    }

    @PutMapping
    public ResponseEntity<Rol> update(
            @Valid
            @RequestHeader("Authorization") String authorization,
            @Valid
            @RequestBody Rol rol
    ){
        return ResponseEntity.ok(rolService.update(authorization, rol));
    }
}
