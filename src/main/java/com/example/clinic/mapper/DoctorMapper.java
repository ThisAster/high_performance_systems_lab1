/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.example.clinic.mapper;

 import org.springframework.stereotype.Component;
 
 import com.example.clinic.dto.DoctorDto;
 import com.example.clinic.entity.Doctor;
 
 /**
  *
  * @author thisaster
  */

 @Component
 public class DoctorMapper {
 
     public DoctorDto entityToDoctorDto(Doctor doctor) {
         if (doctor == null) {
             return null;
         }
         return new DoctorDto(
            doctor.getId(),
            doctor.getName(),
            doctor.getSpeciality(),
            doctor.getConsultationCost()
         );
     }
 
     public Doctor doctorDtoToEntity(DoctorDto doctorDto) {
         if (doctorDto == null) {
             return null;
         }
         Doctor doctor = new Doctor();
         doctor.setId(doctorDto.id());
         doctor.setName(doctorDto.name());
         doctor.setSpeciality(doctorDto.speciality());
         doctor.setConsultationCost(doctorDto.consultationCost());
         return doctor;
     }
 }