package com.example.clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;

// TODO : need fix
@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private RecipeRepository recipeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Optional<AppointmentDto> createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        modelMapper.map(appointmentDto, appointment);

            Doctor doctor = doctorRepository.findById(appointmentDto.doctorId()).orElse(null);
            appointment.setDoctor(doctor);

            Patient patient = patientRepository.findById(appointmentDto.patientId()).orElse(null);
            appointment.setPatient(patient);

            Recipe recipe = recipeRepository.findById(appointmentDto.recipeId()).orElse(null);
            appointment.setRecipe(recipe);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return Optional.of(modelMapper.map(savedAppointment, AppointmentDto.class));
    }

    public List<AppointmentDto> getAllAppointments() {
        return ((List<Appointment>) appointmentRepository.findAll()).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Transactional
    public Optional<AppointmentDto> updateAppointment(Long id, AppointmentDto appointmentDto) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setAppointmentDate(appointmentDto.appointmentDate());
            appointment.setDescription(appointmentDto.description());
    
                Doctor doctor = doctorRepository.findById(appointmentDto.doctorId()).orElse(null);
                appointment.setDoctor(doctor);

                Patient patient = patientRepository.findById(appointmentDto.patientId()).orElse(null);
                appointment.setPatient(patient);
    
                Recipe recipe = recipeRepository.findById(appointmentDto.recipeId()).orElse(null);
                appointment.setRecipe(recipe);
    
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return modelMapper.map(updatedAppointment, AppointmentDto.class);
        });
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
