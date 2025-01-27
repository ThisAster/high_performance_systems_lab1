package com.example.clinic.appointment.service;

import com.example.clinic.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.appointment.dto.AppointmentDto;
import com.example.clinic.appointment.dto.AppointmentFilterRequest;
import com.example.clinic.appointment.dto.EmailDto;
import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.entity.AppointmentsType;
import com.example.clinic.appointment.exception.EntityNotFoundException;
import com.example.clinic.appointment.integration.DoctorService;
import com.example.clinic.appointment.integration.EmailService;
import com.example.clinic.appointment.integration.PatientService;
import com.example.clinic.appointment.mapper.AppointmentMapper;
import com.example.clinic.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final String EMAIL_TITLE = "Information about your appointment at ITMO clinic";

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentsTypeService appointmentsTypeService;
    private final EmailService emailService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment createAppointment(AppointmentCreationDTO appointmentDto) {
        AppointmentsType appointmentsType = appointmentsTypeService
                .getAppointmentsType(appointmentDto.getAppointmentTypeId());

        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);

        appointment.setPatient(appointmentDto.getPatientId());
        appointment.setAppointmentType(appointmentsType);

        if (testForAppointmentCollision(appointmentsType.getDoctor(), appointment)) {
            throw new IllegalArgumentException("Appointment collision");
        }


        return appointmentRepository.save(appointment);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment updateAppointment(Long id, AppointmentCreationDTO appointmentDto) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setPatient(appointmentDto.getPatientId());
        appointment.setAppointmentType(appointmentsTypeService.getAppointmentsType(appointmentDto.getAppointmentTypeId()));

        if (testForAppointmentCollision(appointment.getAppointmentType().getDoctor(), appointment)) {
            throw new IllegalArgumentException("Appointment collision");
        }
        var saved = appointmentRepository.save(appointment);

        return saved;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAppointment(Long id) {
        var appointment = getAppointmentById(id);

        appointmentRepository.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + id + " not found"));
    }

    public Page<Appointment> getAppointments(Pageable page) {
        return appointmentRepository.findAll(page);
    }


    public boolean testForAppointmentCollision(Long doctorId, Appointment appointment) {
        LocalDateTime start = appointment.getAppointmentDate();
        LocalDateTime end = appointment.getAppointmentEnd();

        List<Appointment> collisionCandidates = appointmentRepository.findByDoctorIdAndTimeInterval(doctorId, start.toLocalDate().atStartOfDay(), start.plusDays(1).toLocalDate().atStartOfDay());
        collisionCandidates
                .removeIf(a -> (a.getAppointmentDate().isBefore(start) && a.getAppointmentEnd().isBefore(start)));
        collisionCandidates
                .removeIf(a -> (a.getAppointmentDate().isAfter(end) && a.getAppointmentEnd().isAfter(end)));
        collisionCandidates
                .removeIf(a -> a.getId().equals(appointment.getId()));
        return !collisionCandidates.isEmpty();
    }

    private void sendAppointmentEmail(Appointment appointment, String text) {
        var patient = patientService.findById(appointment.getPatient())
                .orElseThrow(() -> new EntityNotFoundException("Patient missed"));
        var doctor = doctorService.getDoctorById(appointment.getAppointmentType().getDoctor());

        String emailText = String.format(
                "Dear %s,\n%s\nYour appointment information:\nDate: %s\nDoctor: %s\n%s",
                patient.name(),
                text,
                appointment.getAppointmentDate(),
                doctor.name(),
                appointment.getAppointmentType().getDescription()
        );
        emailService.sendEmail(new EmailDto(patient.email(), EMAIL_TITLE, emailText));
    }

    public List<AppointmentDto> filter(AppointmentFilterRequest filter) {
        if (filter.doctor() != null) {
            return appointmentRepository.findByDoctorIdAndTimeInterval(
                            filter.doctor(),
                            filter.appointmentTimeFrom(),
                            filter.appointmentTimeTo()
                    ).stream()
                    .map(appointmentMapper::entityToAppointmentDto)
                    .toList();
        }
        if (filter.patients() != null && !filter.patients().isEmpty()) {
            return appointmentRepository.findByPatientIn(filter.patients())
                    .stream()
                    .map(appointmentMapper::entityToAppointmentDto)
                    .toList();
        }
        throw new IllegalArgumentException("Invalid filter\n" + filter);
    }
}