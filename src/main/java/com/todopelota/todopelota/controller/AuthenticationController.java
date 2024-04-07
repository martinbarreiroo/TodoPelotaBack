package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.AuthenticationResponse;
import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.service.AuthenticationService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/Signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody User request
            ) {
        request.setRole(Role.USER); // set the default role to "USER"
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/Login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
