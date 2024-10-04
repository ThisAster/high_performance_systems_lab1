package com.example.clinic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic.service.BillingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/invoice")
    public ResponseEntity<BillingService.Invoice> getInvoice(@RequestParam List<Long> patientIds, @RequestParam Long userId) {
        BillingService.Invoice invoice = billingService.generateInvoice(patientIds, userId);
        return ResponseEntity.ok(invoice);
    }
}
