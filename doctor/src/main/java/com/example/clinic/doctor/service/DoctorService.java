package com.example.clinic.doctor.service;

import com.example.clinic.doctor.dto.DoctorCreationDTO;
import com.example.clinic.doctor.entity.Doctor;
import com.example.clinic.doctor.exception.EntityNotFoundException;
import com.example.clinic.doctor.mapper.DoctorMapper;
import com.example.clinic.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor createDoctor(DoctorCreationDTO doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);

        return doctorRepository.save(doctor);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Doctor updateDoctor(Long id, DoctorCreationDTO doctorDto) {
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
