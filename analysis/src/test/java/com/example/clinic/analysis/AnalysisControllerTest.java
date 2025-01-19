package com.example.clinic.analysis;

import com.example.clinic.analysis.repository.AnalysisRepository;
import com.example.clinic.analysis.service.AnalysisService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalysisControllerTest {

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
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void createAnalysis(@Value("classpath:/analyses/create.json") Resource json) throws Exception {

        long patientId = 1L;

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .queryParam("patientId", Long.toString(patientId))
                .contentType(ContentType.JSON)
                .body(json.getContentAsByteArray())
                .post("/api/analyses")
                .then()
                .statusCode(201);

    }

    @Test
    void updateAnalysis(@Value("classpath:/analyses/update.json") Resource json) throws Exception {
        long analysisId = 2L;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .contentType(ContentType.JSON)
                .body(json.getContentAsByteArray())
                .put("/api/analyses/{id}", analysisId)
                .then().statusCode(200);
    }

    @Test
    void deleteAnalysis() throws Exception {
        long analysisId = 1L;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/api/analyses/{id}", analysisId)
                .then()
                .statusCode(200)
                .body(equalTo("Analysis with id " + analysisId + " successfully deleted."));
    }

    @Test
    void getAnalysisById() throws Exception {
        long analysisId = 1;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/api/analyses/{id}", analysisId)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) analysisId));
    }

    @Test
    void getAnalyses() throws Exception {
        int page = 0;
        int size = 2;
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgres, ""), "sql/test.sql");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .get("/api/analyses")
                .then()
                .statusCode(200)
                .body("size()", equalTo(size));
    }
}