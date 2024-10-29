package com.example.clinic.service.email;

import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.appointment.service.AppointmentService;
import com.example.clinic.app.mail.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.SendFailedException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;
    @Autowired
    private AppointmentService appointmentService;

    @Test
    void sendIncorrectEmailTest() {

        Appointment appointment = appointmentService.getAppointmentById(1L);

        appointment.getPatient().setEmail("m8y93485ynvp938");

        assertThrows(SendFailedException.class,
                () -> emailService.sendAppointmentEmail(appointment, appointment.getPatient().getEmail()));
    }

    @Test
    void sendCorrectEmailTest() {

        Appointment appointment = appointmentService.getAppointmentById(1L);

        String email = "ilya.minyaeff.3@yandex.ru";

        assertDoesNotThrow(() -> emailService.sendAppointmentEmail(appointment, email));
    }
}