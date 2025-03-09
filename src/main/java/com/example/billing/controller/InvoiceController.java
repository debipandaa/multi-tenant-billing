package com.example.billing.controller;

import com.example.billing.entity.Invoice;
import com.example.billing.service.InvoiceService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate")
    public Invoice generateInvoice(@RequestParam String tenantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return invoiceService.generateInvoice(tenantId, start, end);
    }

}
// auto-generated minor edit 3235
// auto-generated minor edit 24699
// auto-generated minor edit 31927
// auto-generated minor edit 16129
// Auto-generated change #57
// Auto-generated change #73
// minor edit 12425
// Auto-generated change #74
// Auto-generated change #77
// Auto-generated change #9
// Auto-generated change #11
