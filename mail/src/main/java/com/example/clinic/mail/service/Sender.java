package com.example.clinic.mail.service;

import com.example.clinic.mail.dto.ConfigEmail;
import com.example.clinic.mail.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class Sender {
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
    private Session buildSession() {

        Properties props = new Properties();
        props.putAll(Map.of(
                "mail.smtp.host", configEmail.getHost(),
                "mail.smtp.ssl.enable", "true",
                "mail.smtp.port", configEmail.getPort(),
                "mail.smtp.auth", "true",
                "mail.smtp.timeout", "2000",
                "mail.smtp.connectiontimeout", "2000"
        ));

        return Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configEmail.getSender(), configEmail.getPassword());
            }
        });
    }

    @SneakyThrows
    public void sendEmail(EmailDto dto) {
        var msg = buildMessage(buildSession(), dto.targetEmail(), dto.title(), dto.text());
        Transport.send(msg);
    }
}
