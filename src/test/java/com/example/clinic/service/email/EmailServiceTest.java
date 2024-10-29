package com.example.clinic.service.email;

import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.appointment.entity.AppointmentsType;
import com.example.clinic.app.mail.service.EmailService;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.user.service.UserService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.SendFailedException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendIncorrectEmailTest() {
        Appointment appointment = new Appointment(
                1L,
                LocalDateTime.now(),
                new Patient(),
                new AppointmentsType()
        );
        String email = "m8y93485ynvp938";

        assertThrows(SendFailedException.class,
                () -> emailService.sendAppointmentEmail(appointment, email));
    }

    @Test
    void sendCorrectEmailTest() {
        Appointment appointment = new Appointment(
                1L,
                LocalDateTime.now(),
                new Patient(),
                new AppointmentsType()
        );
        String email = "ilya.minyaeff.3@yandex.ru";

        assertDoesNotThrow(() -> emailService.sendAppointmentEmail(appointment, email));
    }
}