package com.example.clinic.service.appointment;


import com.example.clinic.app.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.appointment.service.AppointmentService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;


    @Test
    void createAppointmentTest(){
        AppointmentCreationDTO appointmentCreationDTO = new AppointmentCreationDTO(
                LocalDateTime.now(),
                1L,
                1L
        );

        Appointment createdAppointment = appointmentService.createAppointment(appointmentCreationDTO);
        Appointment retrievedAppointment = appointmentService.getAppointmentById(createdAppointment.getId());
        assertNotNull(retrievedAppointment);
    }

    @Test
    void getAppointmentByIdTest(){
        AppointmentCreationDTO appointmentCreationDTO = new AppointmentCreationDTO(
                LocalDateTime.now(),
                1L,
                1L
        );

        Appointment createdAppointment = appointmentService.createAppointment(appointmentCreationDTO);
        Appointment retrievedAppointment = appointmentService.getAppointmentById(createdAppointment.getId());

        assertNotNull(retrievedAppointment);
        assertEquals(retrievedAppointment.getId(), createdAppointment.getId());
        assertEquals(retrievedAppointment.getPatient().getId(), createdAppointment.getPatient().getId());
        assertEquals(retrievedAppointment.getAppointmentType().getId(), createdAppointment.getAppointmentType().getId());
    }

    @Test
    void deleteAppointmentTest(){
        AppointmentCreationDTO appointmentCreationDTO = new AppointmentCreationDTO(
                LocalDateTime.now(),
                1L,
                1L
        );

        Appointment createdAppointment = appointmentService.createAppointment(appointmentCreationDTO);
        appointmentService.deleteAppointment(createdAppointment.getId());
        assertThrows(EntityNotFoundException.class,
                () -> appointmentService.getAppointmentById(createdAppointment.getId()));
    }

    @Test
    void updateAppointmentTest(){
        LocalDateTime localDateTime = LocalDateTime.now();

        AppointmentCreationDTO oldAppointmentCreationDTO = new AppointmentCreationDTO(
                localDateTime,
                1L,
                1L
        );

        AppointmentCreationDTO newAppointmentCreationDTO = new AppointmentCreationDTO(
                localDateTime,
                1L,
                2L
        );

        Appointment createdAppointment = appointmentService.createAppointment(oldAppointmentCreationDTO);
        Appointment updatedAppointment = appointmentService.updateAppointment(createdAppointment.getId(), newAppointmentCreationDTO);

        assertNotNull(updatedAppointment);
        assertEquals(newAppointmentCreationDTO.getAppointmentDate(), updatedAppointment.getAppointmentDate());
        assertEquals(newAppointmentCreationDTO.getPatientId(), updatedAppointment.getPatient().getId());
        assertEquals(newAppointmentCreationDTO.getAppointmentTypeId(), updatedAppointment.getAppointmentType().getId());
    }
}
