package com.example.clinic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InvoiceDTO {
    private List<ConsultationDTO> consultations;
    private double totalCost;
}
