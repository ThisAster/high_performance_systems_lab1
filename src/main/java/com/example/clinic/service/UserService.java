package com.example.clinic.service;

import com.example.clinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
}
