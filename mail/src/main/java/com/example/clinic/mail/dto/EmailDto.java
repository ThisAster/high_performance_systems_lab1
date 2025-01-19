package com.example.clinic.mail.dto;

public record EmailDto(
        String targetEmail,
        String title,
        String text
) {
}
