package com.example.clinic.appointment.dto;

public record EmailDto(
        String targetEmail,
        String title,
        String text
) {
}
