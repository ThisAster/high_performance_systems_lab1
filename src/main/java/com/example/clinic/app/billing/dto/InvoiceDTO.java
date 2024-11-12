package com.example.clinic.app.billing.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvoiceDTO {

    @NotEmpty(message = "Consultations list cannot be empty")
    private List<ConsultationDTO> consultations;

    @NotNull(message = "Total cost is required")
    @Positive(message = "Total cost must be a positive value")
    private BigDecimal totalCost;
}
