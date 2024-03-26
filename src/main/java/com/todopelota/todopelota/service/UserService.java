package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public List<User> getAllUsers();
    public User findByEmail(String email);



}
