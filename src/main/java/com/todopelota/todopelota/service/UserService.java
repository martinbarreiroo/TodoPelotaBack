package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.model.UserUpdateRequest;
import com.todopelota.todopelota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User updateUser(Long userId, UserUpdateRequest request) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(request.getUsername());
                    user.setPosition(request.getPosition());
                    user.setDescription(request.getDescription());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found with ID " + userId));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
    }
}