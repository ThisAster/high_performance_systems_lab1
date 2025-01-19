/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.doctor.repository;

import com.example.clinic.doctor.entity.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author thisaster
 */
@Repository
public interface DoctorRepository extends
        ReactiveCrudRepository<Doctor, Long>,
        ReactiveSortingRepository<Doctor, Long> {
    Flux<Doctor> findAllBy(Pageable pageable);
}
