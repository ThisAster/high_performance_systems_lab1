package com.example.clinic.service;

import org.springframework.stereotype.Service;

import com.example.clinic.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
}
