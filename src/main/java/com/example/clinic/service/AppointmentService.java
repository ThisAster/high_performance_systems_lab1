package com.example.clinic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.AppointmentMapper; 
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment createAppointment(AppointmentDto appointmentDto) {
        Patient patient = patientRepository.findById(appointmentDto.patient_id())
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + appointmentDto.patient_id() + " not found"));
    
        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);
    
        appointment.setPatient(patient);
        
        return appointmentRepository.save(appointment);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Appointment updateAppointment(Long id, AppointmentDto appointmentDto) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        //appointment.setDescription(appointmentDto.description());
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
