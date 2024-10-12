package com.example.clinic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final RecipeRepository recipeRepository;
    private final DoctorMapper doctorMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());
        List<Recipe> recipes = recipeRepository.findByDoctorId(doctor.getId());

        doctor.setAppointments(appointments);
        doctor.setRecipes(recipes);
    
        return doctorRepository.save(doctor);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        doctor.setName(doctorDto.name());
        doctor.setSpeciality(doctorDto.speciality());
        doctor.setConsultationCost(doctorDto.consultationCost());

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

    public Page<Doctor> getAllDoctors(Pageable page) {
        return doctorRepository.findAll(page);
    }
}
