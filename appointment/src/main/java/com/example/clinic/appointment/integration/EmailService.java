package com.example.clinic.appointment.integration;

import com.example.clinic.appointment.dto.EmailDto;

public interface EmailService {
    void sendEmail(EmailDto emailDto);
}
