package com.example.clinic.app.user.service;

import org.springframework.stereotype.Service;

import com.example.clinic.app.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
}
