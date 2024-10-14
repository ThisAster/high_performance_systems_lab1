package com.example.clinic.service;

import com.example.clinic.config.ConfigEmail;
import com.example.clinic.entity.Appointment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final ConfigEmail configEmail;

    @SneakyThrows
    private Message buildMessage(Session session, Appointment appointment, String emailTypeText){
        String receiverEmail = appointment.getPatient().getEmail();
        String receiverName = appointment.getPatient().getName();
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        String appointmentDescription = appointment.getAppointmentType().getDescription();
        String doctorName = appointment.getAppointmentType().getDoctor().getName();

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(configEmail.getSender()));
        InternetAddress[] addresses = {new InternetAddress(receiverEmail)};
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject(configEmail.getTitle());
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
            put("mail.smtp.host", configEmail.getHost());
            put("mail.smtp.ssl.enable", "true");
            put("mail.smtp.port", configEmail.getPort());
            put("mail.smtp.auth", "true");
        }};

        return Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configEmail.getSender(), configEmail.getPassword());
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
    public void sendDeletionEmail(Appointment appointment){
        Session session = buildSession();
        Message msg = buildMessage(session, appointment, "Your appointment has been canceled.");
        Transport.send(msg);
    }
}
