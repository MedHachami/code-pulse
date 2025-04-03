package com.yc.code_pulse_api.service.implementation;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.yc.code_pulse_api.Repository.UserRepository;

public class UserService {
    
    

    @Autowired
    private UserRepository userRepository;

    public List<User> getUserByName(String name) {

        return userRepository.findAll().stream()
            .filter(u-> u.getUsername().equals(name))
            .collect(Collectors.toList());
    }
}
