package com.example.clinic.auth.service;

import com.example.clinic.auth.dto.UserDTO;
import com.example.clinic.auth.entity.RoleEntity;
import com.example.clinic.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;
    private final UserService userService;
    private final KafkaProducerAuthService kafkaProducerAuthService;

    public String logIn(UserDTO userDTO) {
        var user = userDetailsService.loadUserByUsername(userDTO.getLogin());

        if (!passwordEncoder.matches(userDTO.getPass(), user.getPassword())) {
            // Отправка сообщения при неверной авторизации
            try {
                kafkaProducerAuthService.sendMessage("auth-notifications", "Неверный пользователь или пароль");
            } catch (Exception e) {
                System.err.println("Ошибка при отправке сообщения в Kafka: " + e.getMessage());
            }
            throw new BadCredentialsException("Неверный пользователь или пароль");
        }

        // Успешная авторизация
        String jwt = jwtService.generateToken(user);
        String message = "Пользователь " + userDTO.getLogin() + " успешно авторизован";

        try {
            kafkaProducerAuthService.sendMessage("auth-notifications", message);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке сообщения в Kafka: " + e.getMessage());
        }

        return jwt;
    }

    public void registerUser(UserDTO userDTO) {
        RoleEntity roleEntity = roleService.getByRoleName(userDTO.getRole());
        User user = userDTO.toUser();
        user.setPassword(passwordEncoder.encode(userDTO.getPass()));
        user.setRoleId(roleEntity.getId());
        userService.createUser(user);
    }
}