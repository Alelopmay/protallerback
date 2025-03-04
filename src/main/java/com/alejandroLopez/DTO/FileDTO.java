package com.alejandroLopez.DTO;

import java.sql.Date;
import java.util.List;

public class FileDTO {
    private java.sql.Date date;
    private String report;
    private String clientName;
    private String carLicensePlate;
    private String details;
    private Boolean archived;
    private List<Long> employeeIds; // Lista de IDs de empleados

    public FileDTO(Date date, String report, String clientName, String carLicensePlate, String details, Boolean archived, List<Long> employeeIds) {
        this.date = date;
        this.report = report;
        this.clientName = clientName;
        this.carLicensePlate = carLicensePlate;
        this.details = details;
        this.archived = archived;
        this.employeeIds = employeeIds;
    }
    // Getters y Setters
    // ...

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
