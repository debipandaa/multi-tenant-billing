package com.debii.metering_billing.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.debii.metering_billing.dto.LineItem;
import com.debii.metering_billing.entity.Invoice;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGenerationService {

    private final ObjectMapper objectMapper;
    private static final String INVOICE_DIR = "invoices/";

    public PdfGenerationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // Create the directory if it doesn't exist
        new File(INVOICE_DIR).mkdirs();
    }

    public String generateInvoicePdf(Invoice invoice) throws IOException {
        String filePath = INVOICE_DIR + "invoice-" + invoice.id() + ".pdf";
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add content
        document.add(new Paragraph("Invoice #" + invoice.id()).setBold().setFontSize(20));
        document.add(new Paragraph("Tenant ID: " + invoice.tenantId()));
        document.add(new Paragraph("Date: " + invoice.issuedAt()));
        document.add(new Paragraph("\n"));

        // Add table with line items
        Table table = new Table(new float[] { 4, 1, 2, 2 });
        table.addHeaderCell("Description");
        table.addHeaderCell("Quantity");
        table.addHeaderCell("Unit Price");
        table.addHeaderCell("Subtotal");

        List<LineItem> lineItems = objectMapper.readValue(invoice.lineItemsJson(), new TypeReference<>() {
        });
        for (LineItem item : lineItems) {
            table.addCell(item.description());
            table.addCell(String.valueOf(item.quantity()));
            table.addCell("$" + item.unitPrice().toString());
            table.addCell("$" + item.subtotal().toString());
        }
        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total: $" + invoice.totalAmount()).setBold());

        document.close();
        return filePath;
    }
}