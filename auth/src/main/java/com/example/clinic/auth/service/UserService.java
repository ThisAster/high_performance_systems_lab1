package com.example.clinic.auth.service;

import com.example.clinic.auth.entity.User;
import com.example.clinic.auth.repository.UserRepository;
import com.example.clinic.auth.util.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole(roleService.getByRoleId(user.getRoleId()));
        return user;
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistException("User " + user.getLogin() + " already exists");
        }
        userRepository.save(user);
    }
}
