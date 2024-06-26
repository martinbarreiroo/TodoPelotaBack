package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.AuthenticationResponse;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.model.UserUpdateRequest;
import com.todopelota.todopelota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/profile")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthenticationResponse> updateUser(@RequestBody UserUpdateRequest request) {
        Long userId = Long.parseLong(request.getUserId());
        AuthenticationResponse updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthenticationResponse> updateUserPassword(@RequestBody UserUpdateRequest request) {
        Long userId = Long.parseLong(request.getUserId());
        AuthenticationResponse updatedUser = userService.updateUserPassword(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


}