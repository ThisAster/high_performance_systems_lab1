package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.EmailDto;
import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.integration.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailClient implements EmailService {
    @Override
    public void sendEmail(EmailDto dto) {
        //TODO: replace with feign client
        System.out.println(dto);
    }
}
