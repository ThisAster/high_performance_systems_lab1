package com.example.clinic.billing.controller;

import com.example.clinic.billing.dto.InvoiceDTO;
import com.example.clinic.billing.service.BillingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
