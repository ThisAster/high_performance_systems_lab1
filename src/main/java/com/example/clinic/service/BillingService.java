package com.example.clinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.service.BillingService.Invoice;
import com.example.clinic.service.BillingService.Invoice.Consultation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
public class BillingService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Data
    public static class Invoice {
        private Long payerId;
        private List<Consultation> consultations;  
        private double totalCost;

        @Data
        public static class Consultation {
            private String doctorName;
            private String speciality;
            private String patientName;
            private String patientDateOfBirth;
            private double consultationCost;
        }
    }

    public Invoice generateInvoice(List<Long> patientIds, Long userId) {
        List<Invoice.Consultation> consultations = new ArrayList<>();
        double totalCost = 0.0;

        for (Long patientId : patientIds) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found."));

            List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
            List<Invoice.Consultation> patientConsultations = appointments.stream().map(appointment -> {
                Doctor doctor = appointment.getDoctor();
                Invoice.Consultation consultation = new Invoice.Consultation();
                consultation.setDoctorName(doctor.getName());
                consultation.setSpeciality(doctor.getSpeciality());
                consultation.setPatientName(patient.getName());
                consultation.setPatientDateOfBirth(patient.getDateOfBirth().toString());
                consultation.setConsultationCost(doctor.getConsultationCost());
                return consultation;
            }).collect(Collectors.toList());

            consultations.addAll(patientConsultations); 
            totalCost += patientConsultations.stream().mapToDouble(Invoice.Consultation::getConsultationCost).sum();
        }

        Invoice invoice = new Invoice();
        invoice.setPayerId(userId);
        invoice.setConsultations(consultations);
        invoice.setTotalCost(totalCost);

        return invoice;
    }
}
