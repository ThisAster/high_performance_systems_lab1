package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Recipe;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final RecipeRepository recipeRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public Optional<Doctor> createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());
        List<Recipe> recipes = recipeRepository.findByDoctorId(doctor.getId());

        doctor.setAppointments(appointments.stream()
            .collect(Collectors.toList()));

        doctor.setRecipes(recipes.stream()
            .collect(Collectors.toList()));

        Doctor savedDoctor = doctorRepository.save(doctor);
    
        return Optional.of(savedDoctor);
    }
    
    public List<Doctor> getAllDoctors() {
        return ((Collection<Doctor>) doctorRepository.findAll()).stream()
                .collect(Collectors.toList());
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Transactional
    public Optional<Doctor> updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + id + " not found"));

        doctor.setName(doctorDto.name());
        doctor.setSpeciality(doctorDto.speciality());
        doctor.setConsultationCost(doctorDto.consultationCost());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return Optional.of(updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    
    public List<Doctor> findByName(String name) {
        return doctorRepository.findByName(name).stream()
                .collect(Collectors.toList());
    }
    
    public List<Doctor> findBySpeciality(String speciality) {
        return doctorRepository.findBySpeciality(speciality).stream()
                .collect(Collectors.toList());
    }
}
