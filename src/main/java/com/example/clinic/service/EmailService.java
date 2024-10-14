package com.example.clinic.service;

import com.example.clinic.dto.AppointmentCreationDTO;
import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {
    private String senderEmail = "ilya.minyaeff@yandex.ru";
    private String hostSMTP = "smtp.yandex.ru";
    private Integer port = 465;
    private String emailTitle = "Information about your appointment at ITMO clinic";
    private String password = "lol";

    private final AppointmentRepository appointmentRepository;

    @SneakyThrows
    private Message buildMessage(Session session, Appointment appointment, String emailTypeText){
        String receiverEmail = appointment.getPatient().getEmail();
        String receiverName = appointment.getPatient().getName();
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        String appointmentDescription = appointment.getAppointmentType().getDescription();
        String doctorName = appointment.getAppointmentType().getDoctor().getName();

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderEmail));
        InternetAddress[] addresses = {new InternetAddress(receiverEmail)};
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject(emailTitle);
        msg.setSentDate(new Date());

        String emailText = STR.
                """
                Dear \{receiverName},
                \{emailTypeText}
                Your appointment information:
                Date: \{appointmentDate}
                Doctor: \{doctorName}
                \{appointmentDescription}
                """;
        msg.setText(emailText);
        return msg;
    }

    @SneakyThrows
    private Session buildSession(){
        Properties prop = new Properties() {{
            put("mail.smtp.host", hostSMTP);
            put("mail.smtp.ssl.enable", "true");
            put("mail.smtp.port", port);
            put("mail.smtp.auth", "true");
        }};

        return Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });
    }

    @SneakyThrows
    @Transactional
    public void sendCreationEmail(Appointment appointment){
        Session session = buildSession();
        Message msg = buildMessage(session, appointment, "You have signed up for an appointment");
        Transport.send(msg);
    }

    @SneakyThrows
    @Transactional
    public void sendUpdateEmail(Appointment appointment){
        Session session = buildSession();
        Message msg = buildMessage(session, appointment, "Information about your appointment has been updated.");
        Transport.send(msg);
    }

    @SneakyThrows
    @Transactional
    public void sendDeletionEmail(Long id){
        Appointment appointment = appointmentRepository.getReferenceById(id);

        Session session = buildSession();
        Message msg = buildMessage(session, appointment, "Your appointment has been canceled.");
        Transport.send(msg);
    }
}
