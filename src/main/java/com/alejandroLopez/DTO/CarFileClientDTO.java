package com.alejandroLopez.DTO;

import java.util.Date;

public class CarFileClientDTO {
    private Long carId;
    private String carLicensePlate;
    private String carModel;
    private String carCondition;
    private Long clientId;
    private String clientName;
    private String clientPhoneNumber;
    private String clientEmail;
    private Long fileId;
    private java.util.Date fileDate;
    private String fileReport;
    private String fileClientName;
    private String fileCarLicensePlate;
    private String fileDetails;
    private Boolean fileArchived;

    // Constructor
    public CarFileClientDTO(Long carId, String carLicensePlate, String carModel, String carCondition,
                            Long clientId, String clientName, String clientPhoneNumber, String clientEmail,
                            Long fileId, java.util.Date fileDate, String fileReport,
                            String fileClientName, String fileCarLicensePlate, String fileDetails, Boolean fileArchived) {
        this.carId = carId;
        this.carLicensePlate = carLicensePlate;
        this.carModel = carModel;
        this.carCondition = carCondition;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmail = clientEmail;
        this.fileId = fileId;
        this.fileDate = fileDate;
        this.fileReport = fileReport;
        this.fileClientName = fileClientName;
        this.fileCarLicensePlate = fileCarLicensePlate;
        this.fileDetails = fileDetails;
        this.fileArchived = fileArchived;
    }


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileReport() {
        return fileReport;
    }

    public void setFileReport(String fileReport) {
        this.fileReport = fileReport;
    }

    public String getFileClientName() {
        return fileClientName;
    }

    public void setFileClientName(String fileClientName) {
        this.fileClientName = fileClientName;
    }

    public String getFileCarLicensePlate() {
        return fileCarLicensePlate;
    }

    public void setFileCarLicensePlate(String fileCarLicensePlate) {
        this.fileCarLicensePlate = fileCarLicensePlate;
    }

    public String getFileDetails() {
        return fileDetails;
    }

    public void setFileDetails(String fileDetails) {
        this.fileDetails = fileDetails;
    }

    public Boolean getFileArchived() {
        return fileArchived;
    }

    public void setFileArchived(Boolean fileArchived) {
        this.fileArchived = fileArchived;
    }
    // Getters y Setters
    // (agrega los métodos de acceso aquí)

}
