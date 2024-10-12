package com.example.clinic.service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final PatientRepository patientRepository;

    @SneakyThrows
    @Transactional
    public void sendAppointmentEmail(AppointmentDto appointmentDto)  {
        Optional<Patient> patient = patientRepository.findById(appointmentDto.patient_id());
        String to = patient.get().getEmail();

        //FileInputStream fileInputStream = new FileInputStream("config.properties");
        //Properties properties = new Properties();
        //properties.load(fileInputStream);

        //String user = properties.getProperty("mail.user");
        //String password = properties.getProperty("mail.password");
        //String hostSMTP = properties.getProperty("mail.host");
        String user = "ilya.minyaeff@yandex.ru";
        String password = "rodxxznqoxoewnfa";
        String hostSMTP = "smtp.yandex.ru";
        Integer port = 465;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", hostSMTP);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));
        InternetAddress[] addresses = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject("Information about your appointment at ITMO clinic");
        msg.setSentDate(new Date());

        msg.setText("You have successfully signed up for an appointment");

        Transport.send(msg);
    }
}
