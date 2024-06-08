package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.AuthenticationResponse;
import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.service.AuthenticationService;
import com.todopelota.todopelota.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private NotificationService notificationService;


    @PostMapping("/Signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody User request) {
        try {
            request.setRole(Role.USER); // set the default role to "USER"

            // Run the email notification process in a separate thread
            new Thread(() -> {
                notificationService.sendConfirmationEmail(request.getEmail(), request.getUsername());
            }).start();

            return ResponseEntity.ok(authenticationService.register(request));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(ex.getMessage()));
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
