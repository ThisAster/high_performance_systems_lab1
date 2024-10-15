package com.example.clinic.app.mail.service;

import com.example.clinic.config.ConfigEmail;
import com.example.clinic.app.appointment.entity.Appointment;
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
    private Message buildMessage(Session session, String receiverEmail, String emailTitle, String emailText) {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(configEmail.getSender()));
        InternetAddress[] addresses = {new InternetAddress(receiverEmail)};
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject(emailTitle);
        msg.setSentDate(new Date());
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
    public void sendAppointmentEmail(Appointment appointment, String emailTypeText){
        Session session = buildSession();

        String receiverEmail = appointment.getPatient().getEmail();
        String receiverName = appointment.getPatient().getName();
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        String appointmentDescription = appointment.getAppointmentType().getDescription();
        String doctorName = appointment.getAppointmentType().getDoctor().getName();
        String emailText = String.format(
                "Dear %s,\n%s\nYour appointment information:\nDate: %s\nDoctor: %s\n%s",
                receiverName, emailTypeText, appointmentDate, doctorName, appointmentDescription
        );
        String emailTitle = configEmail.getTitles().get("appointment");
        Message msg = buildMessage(session, receiverEmail, emailTitle, emailText);
        Transport.send(msg);
    }
}
