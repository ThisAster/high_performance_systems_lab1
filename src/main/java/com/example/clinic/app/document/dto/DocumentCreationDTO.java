package com.example.clinic.app.document.dto;

import java.time.LocalDate;

public record DocumentCreationDTO(
        String type,
        LocalDate date,
        String content,
        String status
) {}
