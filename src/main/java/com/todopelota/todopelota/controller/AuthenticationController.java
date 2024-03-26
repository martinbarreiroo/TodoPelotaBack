package com.todopelota.todopelota.controller;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/authentication")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @GetMapping("/api/check-authentication")
    public boolean checkAuthentication(@RequestHeader(value="Authorization") String token) {
        // 1. Decode the token to get the email and password
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] tokenParts = decodedToken.split(":");
        String email = tokenParts[0];
        String password = tokenParts[1];

        // 2. Retrieve the user from the database using the email
        User user = userService.findByEmail(email);

        // 3. Compare the password from the token with the password of the user retrieved from the database
        if (user != null && user.getPassword().equals(password)) {
            // 4. If they match, return true
            return true;
        }

        // Otherwise, return false
        return false;
    }
}


