package com.alejandroLopez.service;

import com.alejandroLopez.model.Invoice;
import com.alejandroLopez.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Obtener todas las facturas
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Obtener una factura por su ID
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    // Guardar una nueva factura
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Actualizar una factura existente
    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setSubtotal(updatedInvoice.getSubtotal());
                    invoice.setIssueDate(updatedInvoice.getIssueDate());
                    invoice.setVat(updatedInvoice.getVat());
                    invoice.setTotal(updatedInvoice.getTotal());
                    invoice.setPaymentMethod(updatedInvoice.getPaymentMethod());
                    invoice.setWarranty(updatedInvoice.getWarranty());
                    invoice.setCar(updatedInvoice.getCar());
                    return invoiceRepository.save(invoice);
                })
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con id: " + id));
    }

    // Eliminar una factura
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
