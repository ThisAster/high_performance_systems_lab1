package com.example.clinic.app.billing.controller;

import java.util.List;

import com.example.clinic.app.billing.dto.InvoiceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic.app.billing.service.BillingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/invoice")
    public ResponseEntity<InvoiceDTO> getInvoice(@RequestParam("patients") List<Long> patientIds) {
        InvoiceDTO invoice = billingService.generateInvoice(patientIds);
        return ResponseEntity.ok(invoice);
    }
}
