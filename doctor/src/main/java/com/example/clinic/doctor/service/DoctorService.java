package com.example.clinic.doctor.service;

import com.example.clinic.doctor.dto.DoctorCreationDTO;
import com.example.clinic.doctor.dto.DoctorDto;
import com.example.clinic.doctor.entity.Doctor;
import com.example.clinic.doctor.exception.EntityNotFoundException;
import com.example.clinic.doctor.mapper.DoctorMapper;
import com.example.clinic.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<DoctorDto> createDoctor(DoctorCreationDTO doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);

        return doctorRepository.save(doctor)
                .map(doctorMapper::entityToDoctorDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<DoctorDto> updateDoctor(Long id, DoctorCreationDTO doctorDto) {
        return doctorRepository.findById(id)
                .switchIfEmpty(Mono.error((new EntityNotFoundException("Doctor not found. Id " + id))))
                .flatMap(doc -> {
                    doc.setName(doctorDto.name());
                    doc.setSpeciality(doctorDto.speciality());
                    return doctorRepository.save(doc);
                })
                .map(doctorMapper::entityToDoctorDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> deleteDoctor(Long id) {
        return doctorRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return doctorRepository.deleteById(id).then(Mono.empty());
                    } else {
                        return Mono.error(new EntityNotFoundException("Doctor with id " + id + " not found"));
                    }
                });
    }

    public Mono<DoctorDto> getDoctorById(Long id) {
        return doctorRepository
                .findById(id)
                .map(doctorMapper::entityToDoctorDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Doctor not found. Id " + id)));
    }

    public Mono<Page<DoctorDto>> getDoctors(Pageable page) {
        return doctorRepository.findAllBy(page)
                .map(doctorMapper::entityToDoctorDto)
                .collectList()
                .zipWith(doctorRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), page, p.getT2()));
    }
}