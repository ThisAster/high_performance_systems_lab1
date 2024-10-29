package com.example.clinic.service.doctor;

import com.example.clinic.app.doctor.dto.DoctorCreationDTO;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.doctor.service.DoctorService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @Test
    void createDoctorTest(){
        DoctorCreationDTO doctorCreationDTO = new DoctorCreationDTO(
                "Mike Smith",
                "Surgeon"
        );

        Doctor createdDoctor = doctorService.createDoctor(doctorCreationDTO);
        Doctor retrievedDoctor = doctorService.getDoctorById(createdDoctor.getId());

        assertNotNull(retrievedDoctor);
    }

    @Test
    void getDoctorByIdTest(){
        DoctorCreationDTO doctorCreationDTO = new DoctorCreationDTO(
                "Mike Smith",
                "Surgeon"
        );

        Doctor createdDoctor = doctorService.createDoctor(doctorCreationDTO);
        Doctor retrievedDoctor = doctorService.getDoctorById(createdDoctor.getId());

        assertNotNull(retrievedDoctor);
        assertEquals(retrievedDoctor.getId(), createdDoctor.getId());
        assertEquals(retrievedDoctor.getName(), createdDoctor.getName());
        assertEquals(retrievedDoctor.getSpeciality(), createdDoctor.getSpeciality());
    }

    @Test
    void deleteDoctorTest(){
        DoctorCreationDTO doctorCreationDTO = new DoctorCreationDTO(
                "Mike Smith",
                "Surgeon"
        );
        Doctor createdDoctor = doctorService.createDoctor(doctorCreationDTO);
        doctorService.deleteDoctor(createdDoctor.getId());

        assertThrows(EntityNotFoundException.class,
                () -> doctorService.getDoctorById(createdDoctor.getId()));
    }

    @Test
    void updateDoctorTest(){
        DoctorCreationDTO oldDoctorCreationDTO = new DoctorCreationDTO(
                "Mike Smith",
                "Surgeon"
        );

        DoctorCreationDTO newDoctorCreationDTO = new DoctorCreationDTO(
                "Mike Smith",
                "Surgeon"
        );

        Doctor createdDoctor = doctorService.createDoctor(oldDoctorCreationDTO);
        Doctor updatedDoctor = doctorService.updateDoctor(createdDoctor.getId(), newDoctorCreationDTO);

        assertNotNull(updatedDoctor);
        assertEquals(newDoctorCreationDTO.name(), updatedDoctor.getName());
        assertEquals(newDoctorCreationDTO.speciality(), updatedDoctor.getSpeciality());
    }
}
