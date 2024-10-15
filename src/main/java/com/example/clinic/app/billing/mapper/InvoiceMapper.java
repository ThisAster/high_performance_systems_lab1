package com.example.clinic.app.billing.mapper;

import com.example.clinic.app.billing.dto.ConsultationDTO;
import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.billing.mapper.ConsultationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {

    private final ConsultationMapper consultationMapper;

    public InvoiceDTO appointmentListToInvoice(List<Appointment> appointments) {
        InvoiceDTO.InvoiceDTOBuilder invoiceBuilder = InvoiceDTO.builder();

        BigDecimal totalCost = new BigDecimal(0);

        List<ConsultationDTO> patientConsultations = consultationMapper.appointmentToConsultationDTO(appointments);
        totalCost = totalCost.add(patientConsultations.stream().map(ConsultationDTO::getPrice).reduce(BigDecimal::add).get());


        invoiceBuilder.consultations(patientConsultations);
        invoiceBuilder.totalCost(totalCost);
        return invoiceBuilder.build();
    }
}
