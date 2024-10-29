package com.alejandroLopez.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double subtotal;

    private java.sql.Date issueDate;

    private Double vat;

    private Double total;

    private String paymentMethod;

    private String warranty;

    @ManyToOne
    @JoinColumn(name = "car_license_plate", referencedColumnName = "license_plate")
    private Car car;

    // Constructor vacío
    public Invoice() {}

    // Constructor completo
    public Invoice(Double subtotal, java.sql.Date issueDate, Double vat, Double total, String paymentMethod, String warranty, Car car) {
        this.subtotal = subtotal;
        this.issueDate = issueDate;
        this.vat = vat;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.warranty = warranty;
        this.car = car;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public java.sql.Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(java.sql.Date issueDate) {
        this.issueDate = issueDate;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", subtotal=" + subtotal +
                ", issueDate=" + issueDate +
                ", vat=" + vat +
                ", total=" + total +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", warranty='" + warranty + '\'' +
                ", car=" + car +
                '}';
    }
}