package com.example.clinic.service.billing;


import com.example.clinic.app.billing.service.BillingService;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@Transactional
class BillingServiceIntegrationTest {

    @Autowired
    private BillingService billingService;

    @Autowired
    private PatientRepository patientRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("1234");

    @BeforeEach
    void setup() {
        patientRepository.deleteAll();

        Patient patient1 = new Patient();
        patient1.setName("John Doe");
        patient1.setId(1L);

        Patient patient2 = new Patient();
        patient2.setName("Jane Doe");
        patient2.setId(2L);

        patientRepository.save(patient1);
        patientRepository.save(patient2);
    }
}