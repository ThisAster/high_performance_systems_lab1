package com.example.clinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic.service.BillingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/invoice/{patientId}")
    public ResponseEntity<BillingService.Invoice> getInvoice(@PathVariable Long patientId) {
        BillingService.Invoice invoice = billingService.generateInvoice(patientId);
        return ResponseEntity.ok(invoice);
    }
}
