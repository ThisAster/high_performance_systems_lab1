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
    public Optional<Appointment> createAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);

        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (patient.isPresent() && doctor.isPresent()) {
            appointment.setPatient(patient.get());
            appointment.setDoctor(doctor.get());
            Appointment savedAppointment = appointmentRepository.save(appointment);
            return Optional.of(savedAppointment);
        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Appointment> updateAppointment(Long id, AppointmentDto appointmentDto) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAppointmentDate(appointmentDto.appointmentDate());
            appointment.setDescription(appointmentDto.description());
            return Optional.of(appointmentRepository.save(appointment));
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    } 
}
