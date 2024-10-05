package com.example.clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Optional<AppointmentDto> createAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + patientId + " not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + doctorId + " not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return Optional.of(appointmentMapper.entityToAppointmentDto(savedAppointment));
    }

    @Transactional
    public Optional<Appointment> updateAppointment(Long id, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + id + " not found"));

        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        appointment.setDescription(appointmentDto.description());       
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return Optional.of(updatedAppointment);
    };

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
