package com.example.clinic.app.doctor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.app.doctor.dto.DoctorDto;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.recipe.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.doctor.mapper.DoctorMapper;
import com.example.clinic.app.doctor.repository.DoctorRepository;
import com.example.clinic.app.recipe.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final RecipeRepository recipeRepository;
    private final DoctorMapper doctorMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);

        List<Recipe> recipes = recipeRepository.findByDoctorId(doctor.getId());

        doctor.setRecipes(recipes);
    
        return doctorRepository.save(doctor);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        doctor.setName(doctorDto.name());
        doctor.setSpeciality(doctorDto.speciality());

        return doctorRepository.save(doctor);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
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

    public Page<Doctor> getDoctors(Pageable page) {
        return doctorRepository.findAll(page);
    }
}
