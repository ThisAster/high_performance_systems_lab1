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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<DoctorDto> createDoctor(DoctorCreationDTO doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);

        return doctorRepository.save(doctor).map(doctorMapper::entityToDoctorDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<DoctorDto> updateDoctor(Long id, DoctorCreationDTO doctorDto) {
        return doctorRepository.findById(id)
                .doOnSuccess(doc -> {
                    if(doc == null){
                        throw new EntityNotFoundException("Doctor not found. Id " + id);
                    }
                    doc.setName(doctorDto.name());
                    doc.setSpeciality(doctorDto.speciality());
                    doctorRepository.save(doc);
                })
                .map(doctorMapper::entityToDoctorDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> deleteDoctor(Long id) {
        doctorRepository.existsById(id)
                .doOnSuccess(exists -> {
                    if(exists){
                        doctorRepository.deleteById(id);
                    } else {
                        throw new EntityNotFoundException("Doctor with id " + id + " not found");
                    }
                });
        return Mono.empty();
    }

    public Mono<DoctorDto> getDoctorById(Long id) {
        return doctorRepository
                .findById(id)
                .map(doctorMapper::entityToDoctorDto);
    }

    public Mono<Page<DoctorDto>> getDoctors(Pageable page) {
        return doctorRepository.findAllBy(page)
                .map(doctorMapper::entityToDoctorDto)
                .collectList()
                .zipWith(this.doctorRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), page, p.getT2()));
    }
}
