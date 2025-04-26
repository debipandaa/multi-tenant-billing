package com.example.billing.util;

import com.example.billing.entity.LineItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PdfInvoiceGenerator {

    public static String generateInvoicePdf(String tenantName,
            UUID invoiceId,
            List<LineItem> lineItems,
            BigDecimal totalAmount) {
        File dir = new File("invoices");
        if (!dir.exists()) {
            dir.mkdirs(); // create directory if missing
        }
        String fileName = "invoices/" + invoiceId + ".pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("Invoice for " + tenantName));
            document.add(new Paragraph("Invoice ID: " + invoiceId.toString()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Description");
            table.addCell("Quantity");
            table.addCell("Unit Price");
            table.addCell("Amount");

            for (LineItem item : lineItems) {
                table.addCell(item.getDescription());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getUnitPrice().toPlainString());
                table.addCell(item.getAmount().toPlainString());
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: $" + totalAmount.toPlainString()));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
        return fileName;
    }
}
// auto-generated minor edit 20611
// auto-generated minor edit 4407
// auto-generated minor edit 5929
// auto-generated minor edit 9128
// Auto-generated change #39
// minor edit 32573
// Auto-generated change #32
// Auto-generated change #51
// Auto-generated change #17
