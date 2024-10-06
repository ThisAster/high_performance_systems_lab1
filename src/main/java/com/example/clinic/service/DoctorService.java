package com.example.clinic.service;

import java.util.List;
import java.util.Optional;

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

        doctor.setAppointments(appointments);

        doctor.setRecipes(recipes);

        Doctor savedDoctor = doctorRepository.save(doctor);
    
        return Optional.of(savedDoctor);
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
}
