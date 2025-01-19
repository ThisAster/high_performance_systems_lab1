package com.example.clinic.appointment.integration;

import com.example.clinic.appointment.entity.Appointment;

public interface EmailService {
    void sendAppointmentEmail(Appointment appointment, String s);
}
