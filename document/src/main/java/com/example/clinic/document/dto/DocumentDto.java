/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.example.clinic.document.dto;

/**
 * @author thisaster
 */

import java.time.LocalDate;

public record DocumentDto(
        Long id,
        String type,
        LocalDate date,
        String content,
        String status
) {
}