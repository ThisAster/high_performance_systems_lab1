package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.integration.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailClient implements EmailService {
    @Override
    public void sendAppointmentEmail(Appointment appointment, String s) {
        System.out.println(appointment.getAppointmentType().getName());
        System.out.println(s);
    }
}
