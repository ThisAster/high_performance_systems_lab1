package com.example.clinic.service.patient;



import com.example.clinic.app.patient.dto.PatientCreationDTO;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.patient.service.PatientService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    void createPatientTest(){
        PatientCreationDTO patientDto = new PatientCreationDTO(
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(patientDto);
        Patient retrievedPatient = patientService.getPatientById(createdPatient.getId());

        assertNotNull(retrievedPatient);
    }

    @Test
    void getPatientByIdTest(){
        PatientCreationDTO patientDto = new PatientCreationDTO(
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
    void getPatientByIdNotFoundTest() {
        Long nonExistentId = 999L;

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> patientService.getPatientById(nonExistentId));

        assertEquals("Patient with id 999 not found", exception.getMessage());
    }

    @Test
    void deletePatientTest(){
        PatientCreationDTO patientDto = new PatientCreationDTO(
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        Patient createdPatient = patientService.createPatient(patientDto);
        patientService.deletePatient(createdPatient.getId());

        assertThrows(EntityNotFoundException.class,
                () -> patientService.getPatientById(createdPatient.getId()));
    }

    @Test
    void updatePatientTest(){
        PatientCreationDTO oldPatientDto = new PatientCreationDTO(
                "patient-lolik",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "email@example.com");
        PatientCreationDTO newPatientDto = new PatientCreationDTO(
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

    @Test
    void getPatientsWithAppointmentsByIdsTest() {
        Set<Patient> patients = patientService.getPatientsWithAppointmentsByIds(Set.of(1L, 2L));
        assertNotNull(patients);
        assertEquals(2, patients.size());
        assertTrue(patients.contains(patientService.getPatientById(1L)));
        assertTrue(patients.contains(patientService.getPatientById(2L)));

    }

    @Test
    void getPatientsWithAppointmentsByIdsNotFoundTest() {
        Set<Long> nonExistentIds = Set.of(999L, 1000L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> patientService.getPatientsWithAppointmentsByIds(nonExistentIds));

        assertEquals("No patients found for the provided IDs", exception.getMessage());
    }
}
