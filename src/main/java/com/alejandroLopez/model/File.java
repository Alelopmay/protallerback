package com.alejandroLopez.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "File")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.sql.Date date;

    @Column(columnDefinition = "TEXT")
    private String report;

    private String clientName;

    private String carLicensePlate;

    @Column(columnDefinition = "TEXT")
    private String details;

    private Boolean archived;

    // Constructor vacío
    public File() {}

    // Constructor completo
    public File(java.sql.Date date, String report, String clientName, String carLicensePlate, String details, Boolean archived) {
        this.date = date;
        this.report = report;
        this.clientName = clientName;
        this.carLicensePlate = carLicensePlate;
        this.details = details;
        this.archived = archived;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", date=" + date +
                ", report='" + report + '\'' +
                ", clientName='" + clientName + '\'' +
                ", carLicensePlate='" + carLicensePlate + '\'' +
                ", details='" + details + '\'' +
                ", archived=" + archived +
                '}';
    }
}