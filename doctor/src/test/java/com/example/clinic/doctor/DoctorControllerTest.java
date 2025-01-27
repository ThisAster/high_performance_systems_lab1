package com.example.clinic.doctor;

import com.example.clinic.doctor.repository.DoctorRepository;
import com.example.clinic.doctor.service.DoctorService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(DoctorControllerTest.class.getName());

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    @Qualifier("test_token")
    private String token;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

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

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" + postgres.getHost() + ":" + postgres.getMappedPort(5432) + "/test");
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void createDoctor(@Value("classpath:/doctors/create.json") Resource json) throws Exception {
        given()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .body(json.getContentAsByteArray())
                .when()
                .post("/api/doctors")
                .then()
                .statusCode(201)
                .assertThat()
                .body("name", equalTo("sam"));

    }

    @Test
    void updateDoctor(@Value("classpath:/doctors/update.json") Resource json) throws Exception {
        Long doctorId = 1L;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .body(json.getContentAsByteArray())
                .when()
                .put("/api/doctors/{id}", doctorId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("bob"));
    }

    @Test
    void deleteDoctor() throws Exception {
        Long doctorId = 1L;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/api/doctors/{id}", doctorId)
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalTo("Doctor with id " + doctorId + " successfully deleted."));
    }

    @Test
    void getDoctorById() throws Exception {
        Long doctorId = 1L; // Replace with actual ID
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/api/doctors/{id}", doctorId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Dr. Alice Brown"))
                .body("id", equalTo(1));
    }

    @Test
    void getAllDoctors() throws Exception {
        int page = 0;
        int size = 2;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .param("page", page)
                .param("size", size)
                .get("/api/doctors")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Dr. Bob Lee"))
                .body("size()", is(size));
    }
}
