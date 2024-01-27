package com.example.clinic.auth.service;

import com.example.clinic.auth.dto.UserDTO;
import com.example.clinic.auth.entity.User;
import com.example.clinic.auth.repository.RoleRepository;
import com.example.clinic.auth.repository.UserRepository;
import com.example.clinic.auth.util.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AuthServiceTest {

    static UserDTO userDTO;
    static User user;

    // Контейнер PostgreSQL
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    // Контейнер Kafka с 1 брокером
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Настройка PostgreSQL
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);

        // Настройка Kafka
        kafka.start();
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void beforeEach() {
        userDTO = new UserDTO(0, "login", "login@test.ru", "test", "USER");
        user = userDTO.toUser();
        userRepository.deleteAll();
    }

    @Test
    void logIn() {
        authService.registerUser(userDTO);

        assertThat(authService.logIn(userDTO)).isNotNull()
                .satisfies(
                        a -> {
                            assertThat(jwtService.extractUserName(a)).isEqualTo(userDTO.getLogin());
                        });
        assertThrows(BadCredentialsException.class, () -> {
            userDTO.setPass("wrong");
            authService.logIn(userDTO);
        });
    }

    @Test
    void registerUser() {
        authService.registerUser(userDTO);

        assertThat(userRepository.findByLogin(userDTO.getLogin())).isNotNull()
                .satisfies(
                        a -> {
                            assertThat(a.get().getLogin()).isEqualTo(userDTO.getLogin());
                            assertThat(a.get().getEmail()).isEqualTo(userDTO.getEmail());
                        });

        assertThrows(UserAlreadyExistException.class, () -> authService.registerUser(userDTO));
    }
}