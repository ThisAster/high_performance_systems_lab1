package com.example.clinic.app.billing.mapper;

import com.example.clinic.app.billing.dto.ConsultationDTO;
import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.billing.mapper.ConsultationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {

    private final ConsultationMapper consultationMapper;

    public InvoiceDTO appointmentListToInvoice(List<Appointment> appointments) {
        InvoiceDTO.InvoiceDTOBuilder invoiceBuilder = InvoiceDTO.builder();

        double totalCost = 0.0;

        List<ConsultationDTO> patientConsultations = consultationMapper.appointmentToConsultationDTO(appointments);
        totalCost += patientConsultations.stream().mapToDouble(ConsultationDTO::getPrice).sum();


        invoiceBuilder.consultations(patientConsultations);
        invoiceBuilder.totalCost(totalCost);
        return invoiceBuilder.build();
    }
}
