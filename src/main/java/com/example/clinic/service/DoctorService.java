package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor; 
import com.example.clinic.entity.Recipe;
import com.example.clinic.repository.DoctorRepository;

import jakarta.transaction.Transactional;

// TODO: need fix
@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private ModelMapper doctorMapper;
    private AppointmentService appointmentService;
    private RecipeService recipeService;

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setDoctorMapper(ModelMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Autowired
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Transactional
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.map(doctorDto, Doctor.class);
    
        List<Appointment> appointments = appointmentService.getAllAppointments().stream()
            .filter(appointmentDto -> appointmentDto.doctorId() == null) 
            .map(appointmentDto -> {
                Appointment appointment = appointmentService.getAppointmentById(appointmentDto.id())
                    .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + appointmentDto.id() + " not found"));
                appointment.setDoctor(doctor); 
                return appointment;
            }).collect(Collectors.toList());
    
        doctor.setAppointments(appointments);
    
        List<Recipe> recipes = recipeService.findByDoctorId(null).stream()
            .map(recipeDto -> {
                Recipe recipe = recipeService.getRecipeById(recipeDto.id())
                    .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + recipeDto.id() + " not found"));
                recipe.setDoctor(doctor); 
                return recipe;
            }).collect(Collectors.toList());
    
        doctor.setRecipes(recipes);
    
        Doctor savedDoctor = doctorRepository.save(doctor);
    
        return doctorMapper.map(savedDoctor, DoctorDto.class);
    }
    

    public List<DoctorDto> getAllDoctors() {
        return ((Collection<Doctor>) doctorRepository.findAll()).stream()
                .map(doctor -> doctorMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Transactional
    public Optional<DoctorDto> updateDoctor(Long id, DoctorDto doctorDto) {
        return doctorRepository.findById(id).map(doctor -> {
            doctor.setName(doctorDto.name());
            doctor.setSpeciality(doctorDto.speciality());
            doctor.setConsultationCost(doctorDto.consultationCost());

            List<Appointment> updatedAppointments = appointmentService.getAllAppointments().stream()
                .filter(appointmentDto -> appointmentDto.doctorId() == null) 
                .map(appointmentDto -> {
                    Appointment appointment = appointmentService.getAppointmentById(appointmentDto.id())
                        .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + appointmentDto.id() + " not found"));
                    appointment.setDoctor(doctor); 
                    return appointment;
                }).collect(Collectors.toList());
            doctor.setAppointments(updatedAppointments);

            List<Recipe> updatedRecipes = recipeService.findByDoctorId(null).stream() 
                .map(recipeDto -> {
                    Recipe recipe = recipeService.getRecipeById(recipeDto.id())
                        .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + recipeDto.id() + " not found"));
                    recipe.setDoctor(doctor); 
                    return recipe;
                }).collect(Collectors.toList());
            doctor.setRecipes(updatedRecipes);

            Doctor updatedDoctor = doctorRepository.save(doctor);
            return doctorMapper.map(updatedDoctor, DoctorDto.class);
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
                .filter(appointmentDto -> appointmentDto.doctorId().equals(doctorId)) 
                .collect(Collectors.toList());
    }

    public List<RecipeDto> getRecipesByDoctorId(Long doctorId) {
        return recipeService.findByDoctorId(doctorId);
    }

    public List<DoctorDto> findByName(String name) {
        return doctorRepository.findByName(name).stream()
                .map(doctor -> doctorMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    public List<DoctorDto> findBySpeciality(String speciality) {
        return doctorRepository.findBySpeciality(speciality).stream()
                .map(doctor -> doctorMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
}
