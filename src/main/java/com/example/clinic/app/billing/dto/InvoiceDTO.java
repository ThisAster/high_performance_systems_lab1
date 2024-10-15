package com.example.clinic.app.billing.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InvoiceDTO {
    private List<ConsultationDTO> consultations;
    private BigDecimal totalCost;
}
