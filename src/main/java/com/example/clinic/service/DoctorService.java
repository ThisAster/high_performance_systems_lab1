package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.RecipeMapper;
import com.example.clinic.repository.DoctorRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final RecipeMapper recipeMapper;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentService appointmentService;
    private final RecipeService recipeService;

    @Transactional
    public Optional<DoctorDto> createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        
        List<AppointmentDto> appointmentsDto = appointmentService.getByDoctorId(doctor.getId());
        List<RecipeDto> recipesDto = recipeService.getByDoctorId(doctor.getId());

        doctor.setAppointments(appointmentsDto.stream()
            .map(appointmentMapper::appointmentDtoToEntity)
            .collect(Collectors.toList()));

        doctor.setRecipes(recipesDto.stream()
            .map(recipeMapper::recipeDtoToEntity)
            .collect(Collectors.toList()));

        Doctor savedDoctor = doctorRepository.save(doctor);
    
        return Optional.of(doctorMapper.entityToDoctorDto(savedDoctor));
    }
    

    public List<DoctorDto> getAllDoctors() {
        return ((Collection<Doctor>) doctorRepository.findAll()).stream()
                .map(doctorMapper::entityToDoctorDto)
                .collect(Collectors.toList());
    }

    public Optional<DoctorDto> getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::entityToDoctorDto);   
    }

    @Transactional
    public Optional<DoctorDto> updateDoctor(Long id, DoctorDto doctorDto) {
        return doctorRepository.findById(id).map(doctor -> {
            doctorMapper.updateEntityFromDto(doctor, doctorDto);

            Doctor updatedDoctor = doctorRepository.save(doctor);
            return doctorMapper.entityToDoctorDto(updatedDoctor);
        });
    }

    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor with id " + id + " not found");
        }
        doctorRepository.deleteById(id);
    }
    
    public List<AppointmentDto> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentService.getAllAppointments().stream()
                .map(appointmentMapper::appointmentDtoToEntity)
                .filter(appointment -> appointment.getDoctor().getId().equals(doctorId))
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());
    }
    
    public List<DoctorDto> findByName(String name) {
        return doctorRepository.findByName(name).stream()
                .map(doctorMapper::entityToDoctorDto)
                .collect(Collectors.toList());
    }
    
    public List<DoctorDto> findBySpeciality(String speciality) {
        return doctorRepository.findBySpeciality(speciality).stream()
                .map(doctorMapper::entityToDoctorDto)
                .collect(Collectors.toList());
    }
}
