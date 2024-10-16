package com.example.clinic.app.billing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.clinic.app.patient.service.PatientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.billing.mapper.InvoiceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final PatientService patientService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceDTO generateInvoice(List<Long> patientIds) {

        Set<Long> patientIdSet = new HashSet<>(patientIds);

        Set<Patient> patients = patientService.getPatientsWithAppointmentsByIds(patientIdSet);


        patientIdSet.removeAll(patients.stream().map(Patient::getId).collect(Collectors.toSet()));

        if (!patientIdSet.isEmpty()) {
            throw new EntityNotFoundException("Patients with ids " + StringUtils.join(patientIdSet, ',') +  " not found.");
        }

        List<Appointment> appointments = patients.stream().flatMap((patient) -> patient.getAppointments().stream()).collect(Collectors.toList());

        return invoiceMapper.appointmentListToInvoice(appointments);
    }
    
}
