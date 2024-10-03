/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Doctor;

/**
 *
 * @author thisaster
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByName(String name);

    List<Doctor> findBySpeciality(String speciality);
}
