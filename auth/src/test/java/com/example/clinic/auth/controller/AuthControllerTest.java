package com.example.clinic.auth.controller;

import com.example.clinic.auth.dto.UserDTO;
import com.example.clinic.auth.entity.User;
import com.example.clinic.auth.repository.RoleRepository;
import com.example.clinic.auth.repository.UserRepository;
import com.example.clinic.auth.service.AuthService;
import com.example.clinic.auth.service.JwtService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AuthControllerTest {

    static UserDTO userDTO;
    static User user;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    // Контейнер Kafka с 1 брокером
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    JwtService jwtService;

    @Value("${auth-service.token}")
    String token;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @LocalServerPort
    private Integer port;

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

    @BeforeAll
    static void beforeAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        RestAssured.config = RestAssured.config()
                .objectMapperConfig(
                        RestAssured.config().getObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper)
                );
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port;

        userDTO = new UserDTO(0, "login", "login@test.ru", "test", "USER");
        user = userDTO.toUser();
        userRepository.deleteAll();
    }

    @Test
    void login() {
        authService.registerUser(userDTO);

        given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .header("Authorization", Matchers.startsWith("Bearer "));

        given()
                .contentType(ContentType.JSON)
                .body("[0, 1, 2]")
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(400);

        userDTO.setLogin("");

        given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(403);
    }
}