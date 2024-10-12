package com.example.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final RecipeRepository recipeRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public Doctor createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());
        List<Recipe> recipes = recipeRepository.findByDoctorId(doctor.getId());

        doctor.setRecipes(recipes);
    
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        doctor.setName(doctorDto.name());
        doctor.setSpeciality(doctorDto.speciality());

        return doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor with id " + id + " not found");
        }
        doctorRepository.deleteById(id);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));
    } 
}
