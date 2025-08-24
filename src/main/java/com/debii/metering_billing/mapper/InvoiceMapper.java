package com.debii.metering_billing.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.debii.metering_billing.dto.InvoiceResponse;
import com.debii.metering_billing.dto.LineItem;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import com.debii.metering_billing.entity.Invoice;
import java.util.List;

@Component
public class InvoiceMapper {

    private final ObjectMapper objectMapper;

    public InvoiceMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows // Lombok annotation to avoid checked exception boilerplate
    public InvoiceResponse toDto(Invoice invoice) {
        List<LineItem> lineItems = objectMapper.readValue(
                invoice.lineItemsJson().asString(),
                new TypeReference<>() {
                });

        return new InvoiceResponse(
                invoice.id(),
                invoice.tenantId(),
                invoice.issuedAt(),
                invoice.totalAmount(),
                invoice.status(),
                invoice.pdfUrl(),
                lineItems);
    }
}