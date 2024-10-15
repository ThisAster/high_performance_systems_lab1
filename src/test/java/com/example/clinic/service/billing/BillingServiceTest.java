package com.example.clinic.service.billing;

import com.example.clinic.app.billing.service.BillingService;
import com.example.clinic.app.patient.repository.PatientRepository;
import com.example.clinic.app.doctor.repository.DoctorRepository;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class BillingServiceTest {

    @Autowired
    private BillingService billingService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @BeforeEach
    void setup() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testGenerateInvoicePatientNotFound() {
        List<Long> invalidPatientIds = Arrays.asList(999L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            billingService.generateInvoice(invalidPatientIds);
        });

        assertEquals("Patients with ids 999 not found.", exception.getMessage());
    }
}
