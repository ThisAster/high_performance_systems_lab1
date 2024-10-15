package com.example.clinic.service.patient;



import com.example.clinic.app.patient.dto.PatientDto;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.patient.repository.PatientRepository;
import com.example.clinic.app.patient.service.PatientService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class PatientServiceTest {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void setup() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void createPatientTest(){
        PatientDto patientDto = new PatientDto(
                1L,
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(patientDto);
        Patient retrievedPatient = patientService.getPatientById(createdPatient.getId());

        assertNotNull(retrievedPatient);
    }

    @Test
    void getPatientByIdTest(){
        PatientDto patientDto = new PatientDto(
                1L,
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(patientDto);
        Patient retrievedPatient = patientService.getPatientById(createdPatient.getId());

        assertNotNull(retrievedPatient);
        assertEquals(retrievedPatient.getId(), createdPatient.getId());
        assertEquals(retrievedPatient.getName(), createdPatient.getName());
        assertEquals(retrievedPatient.getDateOfBirth(), createdPatient.getDateOfBirth());
        assertEquals(retrievedPatient.getEmail(), createdPatient.getEmail());
    }

    @Test
    void deletePatientTest(){
        PatientDto patientDto = new PatientDto(
                1L,
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(patientDto);
        patientService.deletePatient(createdPatient.getId());
        Patient retrievedPatient = patientService.getPatientById(createdPatient.getId());

        assertNull(retrievedPatient);
    }

    @Test
    void updatePatientTest(){
        PatientDto oldPatientDto = new PatientDto(
                1L,
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        PatientDto newPatientDto = new PatientDto(
                1L,
                "patient-bolik",
                LocalDate.of(Integer.parseInt("2000"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(oldPatientDto);
        Patient updatedPatient = patientService.updatePatient(createdPatient.getId(), newPatientDto);

        assertNotNull(updatedPatient);
        assertEquals(newPatientDto.name(), updatedPatient.getName());
        assertEquals(newPatientDto.dateOfBirth(), updatedPatient.getDateOfBirth());
        assertEquals(newPatientDto.email(), updatedPatient.getEmail());
    }
}
