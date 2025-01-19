package com.example.clinic.document.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DocumentCreationDTO(

        @NotBlank(message = "Type is required")
        @Size(max = 100, message = "Type should not exceed 100 characters")
        String type,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotBlank(message = "Content is required")
        @Size(max = 1000, message = "Content should not exceed 1000 characters")
        String content,

        @NotBlank(message = "Status is required")
        @Size(max = 50, message = "Status should not exceed 50 characters")
        String status
) {
}
