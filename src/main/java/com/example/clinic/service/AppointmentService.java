package com.example.clinic.service;

import com.example.clinic.dto.AppointmentCreationDTO;
import com.example.clinic.entity.AppointmentsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.AppointmentMapper; 
import com.example.clinic.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentsTypeService appointmentsTypeService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment createAppointment(AppointmentCreationDTO appointmentDto) {
        Patient patient = patientService.getPatientById(appointmentDto.getPatient_id());

        AppointmentsType appointmentsType = appointmentsTypeService
                .getAppointmentsType(appointmentDto.getAppointment_type_id());

        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);
    
        appointment.setPatient(patient);
        appointment.setAppointmentType(appointmentsType);
        
        return appointmentRepository.save(appointment);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment updateAppointment(Long id, AppointmentCreationDTO appointmentDto) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setAppointmentDate(appointmentDto.getAppointment_date());
        appointment.setPatient(patientService.getPatientById(appointmentDto.getPatient_id()));
        appointment.setAppointmentType(appointmentsTypeService.getAppointmentsType(appointmentDto.getAppointment_type_id()));

        return appointmentRepository.save(appointment);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment with id " + id + " not found");
        }
        appointmentRepository.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + id + " not found"));
    }

    public List<Appointment> getAppointmentsByDate(LocalDateTime date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    public Page<Appointment> getAppointments(Pageable page) {
        return appointmentRepository.findAll(page);
    }
}
